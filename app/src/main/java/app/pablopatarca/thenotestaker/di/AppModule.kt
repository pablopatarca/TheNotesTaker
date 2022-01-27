package app.pablopatarca.thenotestaker.di

import android.app.Application
import androidx.room.Room
import app.pablopatarca.thenotestaker.data.NotesDatabase
import app.pablopatarca.thenotestaker.domain.NotesRepository
import app.pablopatarca.thenotestaker.data.NotesRepositoryImpl
import app.pablopatarca.thenotestaker.domain.NotesUseCase
import app.pablopatarca.thenotestaker.domain.TagsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNotesRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(repository: NotesRepository): NotesUseCase {
        return NotesUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun providesTagsUseCase(repository: NotesRepository): TagsUseCase {
        return TagsUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }
}