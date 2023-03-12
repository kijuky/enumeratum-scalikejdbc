package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait IntScalikeJDBCEnum[A <: IntEnumEntry] extends IntEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    IntScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    IntScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    IntScalikeJDBCEnum.parameterBinderFactory()
  }
}

object IntScalikeJDBCEnum {
  def typeBinder[E <: IntEnumEntry](e: IntEnum[E]): TypeBinder[E] = {
    TypeBinder.int.map(e.withValue)
  }

  def optionalTypeBinder[E <: IntEnumEntry](
    e: IntEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.int.map(e.withValueOpt)
  }

  def parameterBinderFactory[E <: IntEnumEntry](): ParameterBinderFactory[E] = {
    new ParameterBinderFactory[E] {
      override def apply(entry: E): ParameterBinderWithValue = {
        new ParameterBinderWithValue() {
          override def value: E = entry
          override def apply(stmt: PreparedStatement, idx: Int): Unit = {
            stmt.setInt(idx, value.value)
          }
        }
      }
    }
  }
}
