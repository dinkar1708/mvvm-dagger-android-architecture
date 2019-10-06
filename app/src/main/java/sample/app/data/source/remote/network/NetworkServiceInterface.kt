package sample.app.data.source.remote.network


import retrofit2.http.GET
import sample.app.employee.data.Employee

/**
 * interface declaration for fetching data from network
 */
interface NetworkServiceInterface {

    @GET("listdemo.json")
    suspend fun getAllEmployees(): retrofit2.Response<List<Employee>>
}
