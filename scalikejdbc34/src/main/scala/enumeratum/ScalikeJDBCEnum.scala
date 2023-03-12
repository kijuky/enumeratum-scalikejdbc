package enumeratum

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait ScalikeJDBCEnum[A <: EnumEntry] extends Enum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    ScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    ScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
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
    new ParameterBinderFactory[E] {
      override def apply(entry: E): ParameterBinderWithValue =
        new ParameterBinderWithValue() {
          override def value: E = entry
          override def apply(stmt: PreparedStatement, idx: Int): Unit = {
            stmt.setString(idx, value.entryName)
          }
        }
    }
  }
}
