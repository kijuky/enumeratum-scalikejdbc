package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait ByteScalikeJDBCEnum[A <: ByteEnumEntry] extends ByteEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    ByteScalikeJDBCEnum.typeBinder(this)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    ByteScalikeJDBCEnum.optionalTypeBinder(this)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] = {
    ByteScalikeJDBCEnum.parameterBinderFactory()
  }
}

object ByteScalikeJDBCEnum {
  def typeBinder[E <: ByteEnumEntry](e: ByteEnum[E]): TypeBinder[E] = {
    TypeBinder.byte.map(e.withValue)
  }

  def optionalTypeBinder[E <: ByteEnumEntry](
    e: ByteEnum[E]
  ): TypeBinder[Option[E]] = {
    TypeBinder.byte.map(e.withValueOpt)
  }

  def parameterBinderFactory[E <: ByteEnumEntry]()
    : ParameterBinderFactory[E] = { (entry: E) =>
    new ParameterBinderWithValue() {
      override def value: E = entry
      override def apply(stmt: PreparedStatement, idx: Int): Unit = {
        stmt.setByte(idx, value.value)
      }
    }
  }
}
