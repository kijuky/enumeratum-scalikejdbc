package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait ShortScalikeJDBCEnum[A <: ShortEnumEntry] extends ShortEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    ShortScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    ShortScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    ShortScalikeJDBCEnum.parameterBinderFactory()
  }
}

object ShortScalikeJDBCEnum {
  def typeBinder[E <: ShortEnumEntry](e: ShortEnum[E]): TypeBinder[E] = {
    TypeBinder.short.map(e.withValue)
  }

  def optionalTypeBinder[E <: ShortEnumEntry](
    e: ShortEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.short.map(e.withValueOpt)
  }

  def parameterBinderFactory[E <: ShortEnumEntry]()
    : ParameterBinderFactory[E] = { (entry: E) =>
    new ParameterBinderWithValue() {
      override def value: E = entry
      override def apply(stmt: PreparedStatement, idx: Int): Unit = {
        stmt.setShort(idx, value.value)
      }
    }
  }
}
