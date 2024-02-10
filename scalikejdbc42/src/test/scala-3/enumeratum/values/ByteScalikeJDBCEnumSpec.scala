package enumeratum.values

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.FixtureAnyFunSpec
import org.scalatest.matchers.should.Matchers._
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

import scala.collection.immutable

class ByteScalikeJDBCEnumSpec
    extends FixtureAnyFunSpec
    with AutoRollback
    with BeforeAndAfterAll {
  override def beforeAll(): Unit = {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton(s"jdbc:h2:mem:${getClass.getSimpleName}", "sa", "")

    implicit val session: DBSession = AutoSession
    sql"""
    create table traffic_table (
      id integer not null primary key,
      traffic_light_value tinyint
    )
    """.execute.apply() shouldBe false
  }

  override def fixture(implicit session: DBSession): Unit = {
    sql"insert into traffic_table values (1, ${1})".update.apply() shouldBe 1
    sql"insert into traffic_table values (2, ${3})".update.apply() shouldBe 1
  }
}

class ByteScalikeJDBCEnumBuiltInStyleSpec extends ByteScalikeJDBCEnumSpec {
  sealed abstract class TrafficLight(override val value: Byte)
      extends ByteEnumEntry
  object TrafficLight extends ByteScalikeJDBCEnum[TrafficLight] {
    case object Red extends TrafficLight(1)
    case object Yellow extends TrafficLight(2)
    case object Green extends TrafficLight(3)

    override val values: immutable.IndexedSeq[TrafficLight] = findValues
  }

  case class TrafficLightRow(id: Int, trafficLight: TrafficLight)
  object TrafficLightRow extends SQLSyntaxSupport[TrafficLightRow] {
    override val tableName: String = "traffic_table"
    override val columns: Seq[String] = Seq("id", "traffic_light_value")

    def apply(rs: WrappedResultSet) =
      new TrafficLightRow(rs.int("id"), rs.get("traffic_light_value"))

    override val nameConverters: Map[String, String] =
      Map("^trafficLight$" -> "traffic_light_value")
  }

  describe("select") {
    it("use SQLInterpolation") { implicit dbSession =>
      // exercise
      val trafficLightRow: TrafficLightRow =
        sql"select * from traffic_table where id = 1"
          .map(TrafficLightRow.apply)
          .single
          .apply()
          .get

      // verify
      trafficLightRow.id shouldBe 1
      trafficLightRow.trafficLight shouldBe TrafficLight.Red
    }

    it("use QueryDSL") { implicit dbSession =>
      // exercise
      val t = TrafficLightRow.syntax("t")
      @unchecked
      val trafficLightRow: TrafficLightRow = withSQL {
        select.from(TrafficLightRow as t).where.eq(t.id, 1)
      }.map(TrafficLightRow.apply).single.apply().get

      // verify
      trafficLightRow.id shouldBe 1
      trafficLightRow.trafficLight shouldBe TrafficLight.Red
    }
  }

  describe("insert") {
    it("use SQLInterpolation") { implicit dbSession =>
      // exercise
      sql"insert into traffic_table (id, traffic_light_value) values (3, ${3})".update
        .apply() shouldBe 1

      // verify
      val trafficLightRow: TrafficLightRow =
        sql"select * from traffic_table where id = 3"
          .map(TrafficLightRow.apply)
          .single
          .apply()
          .get
      trafficLightRow.trafficLight shouldBe TrafficLight.Green
    }

    it("use QueryDSL") { implicit dbSession =>
      // exercise
      val c = TrafficLightRow.column
      applyUpdate {
        insert
          .into(TrafficLightRow)
          .namedValues(
            c.id -> 3,
            c.trafficLight -> (TrafficLight.Green: TrafficLight)
          )
      } shouldBe 1

      // verify
      val t = TrafficLightRow.syntax("t")
      val trafficLightRow: TrafficLightRow = withSQL {
        select.from(TrafficLightRow as t).where.eq(t.id, 3)
      }.map(TrafficLightRow.apply).single.apply().get
      trafficLightRow.trafficLight shouldBe TrafficLight.Green
    }
  }
}

class ByteScalikeJDBCEnumPluginStyleSpec extends ByteScalikeJDBCEnumSpec {
  sealed abstract class TrafficLight(override val value: Byte)
      extends ByteEnumEntry
  object TrafficLight extends ByteEnum[TrafficLight] {
    case object Red extends TrafficLight(1)
    case object Yellow extends TrafficLight(2)
    case object Green extends TrafficLight(3)

    override val values: immutable.IndexedSeq[TrafficLight] = findValues
  }

  case class TrafficLightRow(id: Int, trafficLight: TrafficLight)
  object TrafficLightRow extends SQLSyntaxSupport[TrafficLightRow] {
    override val tableName: String = "traffic_table"
    override val columns: Seq[String] = Seq("id", "traffic_light_value")

    implicit val typeBinder: TypeBinder[TrafficLight] =
      ByteScalikeJDBCEnum.typeBinder(TrafficLight)

    def apply(rs: WrappedResultSet) =
      new TrafficLightRow(rs.int("id"), rs.get("traffic_light_value"))

    override val nameConverters: Map[String, String] =
      Map("^trafficLight$" -> "traffic_light_value")
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[TrafficLight] =
    ByteScalikeJDBCEnum.parameterBinderFactory[TrafficLight]()

  describe("select") {
    it("use SQLInterpolation") { implicit dbSession =>
      // exercise
      val trafficLightRow: TrafficLightRow =
        sql"select * from traffic_table where id = 1"
          .map(TrafficLightRow.apply)
          .single
          .apply()
          .get

      // verify
      trafficLightRow.id shouldBe 1
      trafficLightRow.trafficLight shouldBe TrafficLight.Red
    }

    it("use QueryDSL") { implicit dbSession =>
      // exercise
      val t = TrafficLightRow.syntax("t")
      @unchecked
      val trafficLightRow: TrafficLightRow = withSQL {
        select.from(TrafficLightRow as t).where.eq(t.id, 1)
      }.map(TrafficLightRow.apply).single.apply().get

      // verify
      trafficLightRow.id shouldBe 1
      trafficLightRow.trafficLight shouldBe TrafficLight.Red
    }
  }

  describe("insert") {
    it("use SQLInterpolation") { implicit dbSession =>
      // exercise
      sql"insert into traffic_table (id, traffic_light_value) values (3, ${3})".update
        .apply() shouldBe 1

      // verify
      val trafficLightRow: TrafficLightRow =
        sql"select * from traffic_table where id = 3"
          .map(TrafficLightRow.apply)
          .single
          .apply()
          .get
      trafficLightRow.trafficLight shouldBe TrafficLight.Green
    }

    it("use QueryDSL") { implicit dbSession =>
      // exercise
      val c = TrafficLightRow.column
      applyUpdate {
        insert
          .into(TrafficLightRow)
          .namedValues(
            c.id -> 3,
            c.trafficLight -> (TrafficLight.Green: TrafficLight)
          )
      } shouldBe 1

      // verify
      val t = TrafficLightRow.syntax("t")
      val trafficLightRow: TrafficLightRow = withSQL {
        select.from(TrafficLightRow as t).where.eq(t.id, 3)
      }.map(TrafficLightRow.apply).single.apply().get
      trafficLightRow.trafficLight shouldBe TrafficLight.Green
    }
  }
}
