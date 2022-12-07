package com.ycspl.bingaa

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesContext(@ApplicationContext context: Context) : Context {
        return context
    }

    @Provides
    fun providesDatabase(context: Context): PersonDatabase {
        return Room.databaseBuilder(context, PersonDatabase::class.java, "personDb").build()
    }

    @Provides
    fun providesDao(database: PersonDatabase) : PersonDao{
        return database.getDao()
    }

    @Provides
    fun providesRepo(dao: PersonDao): PersonRepo {
        return PersonRepoImpl(dao)
    }

}