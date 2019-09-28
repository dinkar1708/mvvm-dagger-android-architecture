package sample.app.employee.data

import sample.app.data.Result
import sample.app.data.Result.Success
import sample.app.data.source.remote.network.NetworkServiceInterface

/**
 * Implementation of the data source that adds a latency simulating network.
 */
class EmployeeRemoteDataSource constructor(val networkInterface: NetworkServiceInterface) : EmployeeDataSource {

    override suspend fun getEmployees(): Result<List<Employee>> {
        val d = listOf<Employee>(Employee("1","2","222","222","2222"))
        return Success(d)
    }
/*
    suspend fun getItems() {

        CoroutineScope(Dispatchers.IO).launch {
            // get data from api in io scope
            val response = networkInterface.getAllEmployees()
            // main scope send data on UI
            withContext(Dispatchers.Main) {
                try {
                    Log.d("res", "requestPasswordReset response $response")
                    val responseBody = response.body()
                    Log.d("res", " data $responseBody")
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }*/

}
