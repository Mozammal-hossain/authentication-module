import com.example.authentication.services.network.NetworkService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
   private const val BASE_URL = "http://34.72.136.54:4067/api/v1/auth/"

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(NetworkService::class.java);
    }
}
