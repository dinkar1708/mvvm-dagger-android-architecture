/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.app.employee.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sample.app.data.Result
import sample.app.data.Result.Success
import sample.app.data.Task
import sample.app.di.ApplicationModule.EmployeeRemoteDataSource
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 *
 * To simplify the sample, this repository only uses the local data source only if the remote
 * data source fails. Remote is the source of truth.
 */
class DefaultEmployeeRepository @Inject constructor(
        @EmployeeRemoteDataSource private val tasksRemoteDataSource: EmployeeDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EmployeeRepository {

    private var cachedTasks: ConcurrentMap<String, Task>? = null

    override suspend fun getEmployees(): Result<List<Employee>> {
//        tasksRemoteDataSource.getEmployees()
        val d = listOf<Employee>(Employee("1", "2", "222", "222", "2222"), Employee("122", "2", "222", "222", "2222"))

        return Success(d.plus(d).plus(d))
    }
}
