# enumeratum-scalikejdbc

[enumeratum](https://github.com/lloydmeta/enumeratum)の[scalikejdbc](http://scalikejdbc.org/)サポートです。

# 使い方

[enumeratum-play](https://github.com/lloydmeta/enumeratum#play-integration)と同じです。対応する○○EnumEntryに対して○○ScalikeJDBCEnumを使用します。

```scala
import enumeratum._

sealed trait Greeting extends EnumEntry

object Greeting extends ScalikeJDBCEnum[Greeting] {

  val values = findValues

  case object Hello   extends Greeting
  case object GoodBye extends Greeting
  case object Hi      extends Greeting
  case object Bye     extends Greeting
}
```

TypeBinderとParameterBinderFactoryが提供されます。

# アーティファクト名について

利用しているscalikejdbcのバージョンに応じて、アーティファクトを選択してください。

- scalikejdbc 4.0.x: `enumeratum-scalikejdbc4`
- scalikejdbc 3.5.0: `enumeratum-scalikejdbc35` 主にPlay 2.8ユーザー向け
- scalikejdbc 3.4.2: `enumeratum-scalikejdbc34` 主にScala 2.11ユーザー向け

将来的には、最新バージョン以外はサポートから外す予定です。

# バージョン番号について

`x.y.z.w`で管理します。

最初の`x.y.z`はenumeratumのバージョンと一致します。`w`がこのライブラリのバージョンです。

# 既知の不具合

- Scala3において、Enum/EnumEntryのfindValuesマクロが適切に展開されないため、Scala3に対してはEnum/EnumEntryのサポートを提供していません。
