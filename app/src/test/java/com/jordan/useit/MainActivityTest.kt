package com.jordan.useit

import com.jordan.useit.repository.data.Post
import com.jordan.useit.repository.data.PostAuthor
import com.jordan.useit.repository.data.PostAuthorImage
import com.jordan.useit.repository.data.Posts
import com.jordan.useit.repository.services.IBlogService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [27])
class MainActivityTest {
    private lateinit var blogService: IBlogService
    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        blogService = Mockito.mock(IBlogService::class.java)
        mainActivity = MainActivity(blogService)
    }

    @Test
    fun get200CodeStatusForBlogsList() = runTest {
        val mockCall = Mockito.mock(Call::class.java) as Call<Posts?>
        val mockResponse = Response.success(200, null as Posts?)
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)
        Mockito.`when`(blogService.listPosts()).thenReturn(mockCall)

        mainActivity.getBlogs()

        assertEquals(200, mockResponse.code())
    }

    @Test
    fun getBlogsWithoutImages() = runTest {
        val posts = Posts(
            kind = "blogger#postList",
            items = listOf(
                Post(
                    kind = "blogger#post",
                    id = "1",
                    title = "Post 1",
                    content = "Content 1",
                    images = null,
                    author = PostAuthor("1", "Author 1", PostAuthorImage("author1.png")),
                    url = "url1"
                )
            )
        )
        val mockCall = Mockito.mock(Call::class.java) as Call<Posts?>
        val mockResponse = Response.success(posts)
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)
        Mockito.`when`(blogService.listPosts()).thenReturn(mockCall)

        mainActivity.getBlogs()
        val response = mockResponse.body()?.items
        assertEquals(1, response?.size)
        assertEquals(null, response?.first()?.images)
    }

    @Test
    fun getBlogsWithImages() = runTest {
        val posts = Posts(
            kind = "blogger#postList",
            items = listOf(
                Post(
                    kind = "blogger#post",
                    id = "1",
                    title = "Post 1",
                    content = "Content 1",
                    images = listOf(),
                    author = PostAuthor("1", "Author 1", PostAuthorImage("author1.png")),
                    url = "url1"
                )
            )
        )
        val mockCall = Mockito.mock(Call::class.java) as Call<Posts?>
        val mockResponse = Response.success(posts)
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)
        Mockito.`when`(blogService.listPosts()).thenReturn(mockCall)

        mainActivity.getBlogs()
        val response = mockResponse.body()?.items
        assertEquals(1, response?.size)
        assertEquals(true, response?.first()?.images != null)
    }

}
