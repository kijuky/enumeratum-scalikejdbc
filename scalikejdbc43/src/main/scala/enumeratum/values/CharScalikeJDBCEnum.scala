package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait CharScalikeJDBCEnum[A <: CharEnumEntry] extends CharEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    CharScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    CharScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    CharScalikeJDBCEnum.parameterBinderFactory()
  }
}

object CharScalikeJDBCEnum {
  def typeBinder[E <: CharEnumEntry](e: CharEnum[E]): TypeBinder[E] = {
    TypeBinder.string.map { x => e.withValue(x.charAt(0)) }
  }

  def optionalTypeBinder[E <: CharEnumEntry](
    e: CharEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.string.map(_.headOption.map(e.withValue))
  }

  def parameterBinderFactory[E <: CharEnumEntry]()
    : ParameterBinderFactory[E] = { (entry: E) =>
    new ParameterBinderWithValue() {
      override def value: E = entry
      override def apply(stmt: PreparedStatement, idx: Int): Unit = {
        stmt.setString(idx, value.value.toString)
      }
    }
  }
}
