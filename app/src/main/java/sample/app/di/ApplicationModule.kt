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

package sample.app.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sample.app.data.source.DefaultTasksRepository
import sample.app.data.source.TasksDataSource
import sample.app.data.source.TasksRepository
import sample.app.data.source.local.TasksLocalDataSource
import sample.app.data.source.local.ToDoDatabase
import sample.app.data.source.remote.TasksRemoteDataSource
import sample.app.data.source.remote.network.NetworkServiceInterface
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME


@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksRemoteDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksLocalDataSource

    @JvmStatic
    @Singleton
    @TasksRemoteDataSource
    @Provides
    fun provideTasksRemoteDataSource(ninterface: NetworkServiceInterface): TasksDataSource {
        return TasksRemoteDataSource(ninterface)
    }

    @JvmStatic
    @Singleton
    @TasksLocalDataSource
    @Provides
    fun provideTasksLocalDataSource(
            database: ToDoDatabase,
            ioDispatcher: CoroutineDispatcher
    ): TasksDataSource {
        return TasksLocalDataSource(
                database.taskDao(), ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                "Tasks.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/dinkar1708/APITest/master/apis/")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://raw.githubusercontent.com/dinkar1708/APITest/master/apis/")
                .client(builder.build())
                .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): NetworkServiceInterface {
        return retrofit.create(NetworkServiceInterface::class.java)
    }
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultTasksRepository): TasksRepository
}
