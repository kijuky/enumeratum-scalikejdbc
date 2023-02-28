package enumeratum.values

import scalikejdbc.ParameterBinderFactory
import scalikejdbc.ParameterBinderWithValue
import scalikejdbc.TypeBinder

import java.sql.PreparedStatement

trait LongScalikeJDBCEnum[A <: LongEnumEntry] extends LongEnum[A] {
  implicit val typeBinder: TypeBinder[A] = {
    TypeBinder.long.map(withValue)
  }

  implicit val optionalTypeBinder: TypeBinder[Option[A]] = {
    TypeBinder.long.map(withValueOpt)
  }

  implicit val parameterBinderFactory: ParameterBinderFactory[A] =
    new ParameterBinderFactory[A] {
      override def apply(entry: A): ParameterBinderWithValue =
        new ParameterBinderWithValue() {
          override def value: A = entry
          override def apply(stmt: PreparedStatement, idx: Int): Unit = {
            stmt.setLong(idx, value.value)
          }
        }
    }
}
