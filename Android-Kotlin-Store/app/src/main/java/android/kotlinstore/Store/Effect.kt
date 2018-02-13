package android.kotlinstore.Store

data class Effect (
    var effect: ArrayList<(HashMap<String, Any?>)->Thread?>
)