package com.example.noteapp2.di

import com.example.noteapp2.data.repository.NoteRepositoryImpl
import com.example.noteapp2.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: NoteRepositoryImpl): Repository
}