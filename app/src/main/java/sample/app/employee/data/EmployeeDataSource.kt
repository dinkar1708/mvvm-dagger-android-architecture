package sample.app.employee.data

import sample.app.data.Result

/**
 * Main entry point for accessing tasks data.
 */
interface EmployeeDataSource {

    suspend fun getEmployees(): Result<List<Employee>>

}
