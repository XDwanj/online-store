package cn.xdwanj.onlinestore.response


class WangEditor private constructor(
  var errno: Int? = null,
  var data: List<String>? = null,
) {
  companion object {
    fun success(data: List<String>) =
      WangEditor(WangEditorStatusEnum.SUCCESS.code, data)

    fun error() = WangEditor(WangEditorStatusEnum.ERROR.code, null)
  }
}

enum class WangEditorStatusEnum(
  val code: Int,
  val desc: String
) {
  SUCCESS(0, "上传成功"),
  ERROR(1, "上传失败")
}