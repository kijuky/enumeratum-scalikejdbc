package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait StringScalikeJDBCEnum[A <: StringEnumEntry] extends StringEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    StringScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    StringScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    StringScalikeJDBCEnum.parameterBinderFactory()
  }
}

object StringScalikeJDBCEnum {
  def typeBinder[E <: StringEnumEntry](e: StringEnum[E]): TypeBinder[E] = {
    TypeBinder.string.map(e.withValue)
  }

  def optionalTypeBinder[E <: StringEnumEntry](
    e: StringEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.string.map(e.withValueOpt)
  }

  def parameterBinderFactory[E <: StringEnumEntry]()
    : ParameterBinderFactory[E] = { (entry: E) =>
    new ParameterBinderWithValue() {
      override def value: E = entry
      override def apply(stmt: PreparedStatement, idx: Int): Unit = {
        stmt.setString(idx, value.value)
      }
    }
  }
}
