import com.example.authloginmodule.Cadastro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.authloginmodule.Utils.Constants
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.http.Path

interface Endpoint {
    // VER COMO PARAMETRIZAR ESSE CARA DENTRO DO POST() URGENTE!!!!!!!!
    @POST(Constants.CADASTRO)
    fun register(@Path("type") type: String, @Body usr: JsonObject): Call<ResponseBody>

    @POST(Constants.LOGIN)
    fun login(@Path("type") type: String, @Body usr: JsonObject): Call<ResponseBody>
}
