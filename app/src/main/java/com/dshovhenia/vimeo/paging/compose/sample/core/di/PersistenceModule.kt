package com.dshovhenia.vimeo.paging.compose.sample.core.di

import android.app.Application
import androidx.room.Room
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.VimeoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Singleton
  @Provides
  fun provideDb(app: Application): VimeoDatabase {
    return Room
      .databaseBuilder(app, VimeoDatabase::class.java, "vimeo_database.db")
      .allowMainThreadQueries()
      .build()
  }

}
