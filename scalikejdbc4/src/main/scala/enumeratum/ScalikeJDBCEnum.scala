package enumeratum

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait ScalikeJDBCEnum[E <: EnumEntry] extends Enum[E] {
  implicit val typeBinder: TypeBinder[E] = {
    ScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[E]] = {
    ScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[E] = {
    ScalikeJDBCEnum.parameterBinderFactory()
  }
}

object ScalikeJDBCEnum {
  def typeBinder[E <: EnumEntry](e: Enum[E]): TypeBinder[E] = {
    TypeBinder.string.map(e.withName)
  }

  def optionalTypeBinder[E <: EnumEntry](e: Enum[E]): TypeBinder[Option[E]] = {
    TypeBinder.string.map(e.withNameOption)
  }

  def parameterBinderFactory[E <: EnumEntry](): ParameterBinderFactory[E] = {
    (entry: E) =>
      new ParameterBinderWithValue() {
        override def value: E = entry
        override def apply(stmt: PreparedStatement, idx: Int): Unit = {
          stmt.setString(idx, value.entryName)
        }
      }
  }
}
