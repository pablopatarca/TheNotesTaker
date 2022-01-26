package app.pablopatarca.thenotestaker.di

import android.app.Application
import androidx.room.Room
import app.pablopatarca.thenotestaker.data.NotesDatabase
import app.pablopatarca.thenotestaker.domain.NotesRepository
import app.pablopatarca.thenotestaker.data.NotesRepositoryImpl
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
        return NotesRepositoryImpl(
            notebooksDS = db.notebooksDao,
            notesDS = db.notesDao
        )
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