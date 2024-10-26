package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.entity.data.NoteSelect
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_model.response.ProductResponseDto
import com.hmoa.feature_hbti.viewmodel.NotePickViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

class NotePickViewmodelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val surveyRepository = mock(SurveyRepository::class.java)
    private val hshopRepository = mock(HshopRepository::class.java)
    lateinit var viewmodel: NotePickViewmodel
    lateinit var fakeNoteSelectData: MutableList<NoteSelect>

    companion object {
        val surveyResult = listOf(
            NoteResponseDto(content = "어쩌구저쩌구", noteId = 0, noteName = "그린", notePhotoUrl = "그린사진"),
            NoteResponseDto(content = "어쩌구저쩌구", noteId = 1, noteName = "아쿠아", notePhotoUrl = "아쿠아사진"),
            NoteResponseDto(content = "어쩌구저쩌구", noteId = 2, noteName = "스파이스", notePhotoUrl = "스파이스사진")
        )
        val noteProducts = ResultResponse(
            data = ProductListResponseDto(
                data = listOf(
                    ProductResponseDto(
                        productId = 0,
                        productName = "그린",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "그린사진",
                        isRecommended = true,
                        price = 10
                    ),
                    ProductResponseDto(
                        productId = 1,
                        productName = "아쿠아",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "바다사진",
                        isRecommended = true,
                        price = 10
                    ),
                    ProductResponseDto(
                        productId = 2,
                        productName = "스파이스",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "스파이스사진",
                        isRecommended = true,
                        price = 10
                    ),
                    ProductResponseDto(
                        productId = 3,
                        productName = "머스크",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "머스크사진",
                        isRecommended = false,
                        price = 10
                    ),
                    ProductResponseDto(
                        productId = 4,
                        productName = "프루티",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "과일사진",
                        isRecommended = false,
                        price = 10
                    ),
                    ProductResponseDto(
                        productId = 5,
                        productName = "플라워",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "꽃사진",
                        isRecommended = false,
                        price = 10
                    ),
                )
            )
        )
    }

    @Before
    override fun setUp() {
        runBlocking {
            Mockito.`when`(surveyRepository.getAllSurveyResult()).thenReturn(surveyResult)
            Mockito.`when`(hshopRepository.getNotesProduct()).thenReturn(noteProducts)
            viewmodel = spy(NotePickViewmodel(surveyRepository, hshopRepository))
        }
        fakeNoteSelectData = mutableListOf(
            NoteSelect(
                productId = 0,
                isSelected = true,
                nodeFaceIndex = null,
                isRecommended = true
            ),
            NoteSelect(
                productId = 1,
                isSelected = true,
                nodeFaceIndex = null,
                isRecommended = false
            ),
            NoteSelect(
                productId = 2,
                isSelected = false,
                nodeFaceIndex = null,
                isRecommended = false
            ),
            NoteSelect(
                productId = 3,
                isSelected = false,
                nodeFaceIndex = null,
                isRecommended = false
            ),
        )
    }

    @Test
    fun `test_createViewmodel_TopRecommendedNoteInitialized`() = coroutineRule.runTest {
        val expectedTopNote = "그린"
        viewmodel.getTopRecommendedNote()
        Assert.assertEquals(expectedTopNote, viewmodel.topRecommendedNoteState.value)
    }

    @Test
    fun `test_createViewmodel_noteSelectDataInitialized`() = coroutineRule.runTest {
        val expectedNoteSelectData = noteProducts.data!!.data.map {
            NoteSelect(
                productId = it.productId,
                isSelected = false,
                isRecommended = it.isRecommended,
                nodeFaceIndex = null
            )
        }
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        Assert.assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
    }

    @Test
    fun `test_1NoteSelect_reflectsInNoteSelectData`() = coroutineRule.runTest {
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        val targetNode = noteProducts.data!!.data[0]
        viewmodel.handleNoteSelectData(
            index = 0,
            value = true,
            data = NoteSelect(
                productId = targetNode.productId,
                isSelected = false,
                isRecommended = targetNode.isRecommended,
                nodeFaceIndex = null,
            )
        )
        var expectedNoteSelectData = noteProducts.data!!.data.map {
            NoteSelect(
                productId = it.productId,
                isSelected = false,
                isRecommended = it.isRecommended,
                nodeFaceIndex = null
            )
        }.toMutableList()
        expectedNoteSelectData[0] = NoteSelect(
            productId = targetNode.productId,
            isSelected = true,
            isRecommended = targetNode.isRecommended,
            nodeFaceIndex = null
        )
        Assert.assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
    }

    @Test
    fun `test_changeSelectStateOfSingleNote_reflectInReturn`() = coroutineRule.runTest {
        val targetNote = fakeNoteSelectData.get(0)
        //given: 0번째 노트인 targetNote 취소하기(isSelected = false)
        val result = viewmodel.changeNoteSelectData(0, false, targetNote, fakeNoteSelectData)
        val expectedResult = fakeNoteSelectData.mapIndexed { index, noteSelect ->
            if (index == 0) {
                NoteSelect(
                    productId = targetNote.productId,
                    isSelected = false,
                    nodeFaceIndex = targetNote.nodeFaceIndex,
                    isRecommended = targetNote.isRecommended
                )
            } else {
                noteSelect
            }
        }
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `test_add2NoteSelectSequentially_validateNodeFaceIndexes`() = coroutineRule.runTest {
        var expectedDataAfterNodeSelected = mutableListOf(
            NoteSelect(
                productId = 0,
                isSelected = true,
                nodeFaceIndex = null,
                isRecommended = true
            ),
            NoteSelect(
                productId = 1,
                isSelected = true,
                nodeFaceIndex = 2,
                isRecommended = false
            ),
            NoteSelect(
                productId = 2,
                isSelected = false,
                nodeFaceIndex = null,
                isRecommended = false
            ),
            NoteSelect(
                productId = 3,
                isSelected = false,
                nodeFaceIndex = null,
                isRecommended = false
            ),
        )
        //given1: 첫번째 노트 선택
        val resultAfter1NodeSelected = viewmodel.reorderNoteFaceIndex(fakeNoteSelectData)

        Assert.assertEquals(expectedDataAfterNodeSelected, resultAfter1NodeSelected)
        //given2: 두번째 노트 선택
        resultAfter1NodeSelected.set(
            2, NoteSelect(
                productId = 2,
                isSelected = true,
                nodeFaceIndex = null,
                isRecommended = false
            )
        )
        expectedDataAfterNodeSelected.set(
            2, NoteSelect(
                productId = 2,
                isSelected = true,
                nodeFaceIndex = 3,
                isRecommended = false
            )
        )
        val resultAfter2NodeSelected = viewmodel.reorderNoteFaceIndex(resultAfter1NodeSelected)
        Assert.assertEquals(expectedDataAfterNodeSelected, resultAfter2NodeSelected)
    }

    @Test
    fun `test_nextButtonState_afterInitialize_resultsInFalse`() = coroutineRule.runTest {
        //Given: 초기화
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        //Then: 버튼의 사용가능여부 = false이다
        Assert.assertEquals(false, viewmodel.isNextButtonAvailableState.value)
    }

    @Test
    fun `test_nextButtonState_noteSelect_resultsInTrue`() = coroutineRule.runTest {
        //Given: 초기화
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        val targetNode = noteProducts.data!!.data[0]
        //When:targetNode만 선택한다
        viewmodel.handleNoteSelectData(
            index = 0,
            value = true,
            data = NoteSelect(
                productId = targetNode.productId,
                isSelected = false,
                isRecommended = targetNode.isRecommended,
                nodeFaceIndex = null,
            )
        )
        //Then: 버튼의 사용가능여부 = true이다
        Assert.assertEquals(true, viewmodel.isNextButtonAvailableState.value)
    }

    @Test
    fun `test_nextButtonState_noteSelectAndCancel_resultsInFalse`() = coroutineRule.runTest {
        //Given: 초기화 후 targetNode만 선택한다
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        val targetNode = noteProducts.data!!.data[0]
        viewmodel.handleNoteSelectData(
            index = 0,
            value = true,
            data = NoteSelect(
                productId = targetNode.productId,
                isSelected = false,
                isRecommended = targetNode.isRecommended,
                nodeFaceIndex = null,
            )
        )
        //When: 다시 targetNode 선택을 취소한다
        viewmodel.handleNoteSelectData(
            index = 0,
            value = false,
            data = NoteSelect(
                productId = targetNode.productId,
                isSelected = true,
                isRecommended = targetNode.isRecommended,
                nodeFaceIndex = null,
            )
        )
        //Then: 버튼의 사용가능여부 = false이다
        Assert.assertEquals(false, viewmodel.isNextButtonAvailableState.value)
    }
}