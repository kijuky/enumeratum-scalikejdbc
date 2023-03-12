package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait LongScalikeJDBCEnum[A <: LongEnumEntry] extends LongEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    LongScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    LongScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    LongScalikeJDBCEnum.parameterBinderFactory()
  }
}

object LongScalikeJDBCEnum {
  def typeBinder[E <: LongEnumEntry](e: LongEnum[E]): TypeBinder[E] = {
    TypeBinder.long.map(e.withValue)
  }

  def optionalTypeBinder[E <: LongEnumEntry](
    e: LongEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.long.map(e.withValueOpt)
  }

  def parameterBinderFactory[E <: LongEnumEntry]()
    : ParameterBinderFactory[E] = {
    new ParameterBinderFactory[E] {
      override def apply(entry: E): ParameterBinderWithValue = {
        new ParameterBinderWithValue() {
          override def value: E = entry
          override def apply(stmt: PreparedStatement, idx: Int): Unit = {
            stmt.setLong(idx, value.value)
          }
        }
      }
    }
  }
}
