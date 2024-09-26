package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_domain.entity.data.NoteSelect
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_model.response.ProductResponseDto
import com.hmoa.feature_hbti.viewmodel.NotePickViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                        isRecommended = true
                    ),
                    ProductResponseDto(
                        productId = 1,
                        productName = "아쿠아",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "바다사진",
                        isRecommended = true
                    ),
                    ProductResponseDto(
                        productId = 2,
                        productName = "스파이스",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "스파이스사진",
                        isRecommended = true
                    ),
                    ProductResponseDto(
                        productId = 3,
                        productName = "머스크",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "머스크사진",
                        isRecommended = false
                    ),
                    ProductResponseDto(
                        productId = 4,
                        productName = "프루티",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "과일사진",
                        isRecommended = false
                    ),
                    ProductResponseDto(
                        productId = 5,
                        productName = "플라워",
                        productDetails = "어쩌구저쩌구",
                        productPhotoUrl = "꽃사진",
                        isRecommended = false
                    ),
                )
            )
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
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
        assertEquals(expectedTopNote, viewmodel.topRecommendedNoteState.value)
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
        assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
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
                isSelected = true,
                isRecommended = targetNode.isRecommended,
                nodeFaceIndex = null,
            ),
            noteOrderQuantity = 5,
            selectedNotesOrderQuantity = 0
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
        assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
    }

    @Test
    fun `test_addNoteSelectWhenOrderLimit_noChangeInNoteSelectData`() = coroutineRule.runTest {
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        //when: 이미 3개 수량 중에 3개를 모두 고른 경우
        viewmodel.handleNoteSelectData(
            index = 0,
            value = true,
            data = NoteSelect(
                productId = noteProducts.data!!.data[0].productId,
                isSelected = true,
                isRecommended = noteProducts.data!!.data[0].isRecommended,
                nodeFaceIndex = null,
            ),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 0
        )
        viewmodel.handleNoteSelectData(
            index = 1,
            value = true,
            data = NoteSelect(
                productId = noteProducts.data!!.data[1].productId,
                isSelected = true,
                isRecommended = noteProducts.data!!.data[1].isRecommended,
                nodeFaceIndex = null,
            ),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 1
        )
        viewmodel.handleNoteSelectData(
            index = 2,
            value = true,
            data = NoteSelect(
                productId = noteProducts.data!!.data[2].productId,
                isSelected = true,
                isRecommended = noteProducts.data!!.data[2].isRecommended,
                nodeFaceIndex = null,
            ),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 2
        )
        val expectedNoteSelectData = mutableListOf<NoteSelect>()
        viewmodel.noteSelectDataState.value.map { expectedNoteSelectData.add(it) }
        //given: 1개를 추가로 고르는 경우
        viewmodel.handleNoteSelectData(
            index = 3,
            value = true,
            data = NoteSelect(
                productId = noteProducts.data!!.data[3].productId,
                isSelected = true,
                isRecommended = noteProducts.data!!.data[3].isRecommended,
                nodeFaceIndex = null,
            ),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 3
        )
        //then: 3개 추가까지 반영되고, 마지막 1개는 반영되지 않음
        assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
    }

    @Test
    fun `test_cancelNoteSelectWhenOrderLimit_reflectInNoteSelectData`() = coroutineRule.runTest {
        viewmodel.getTopRecommendedNote()
        launch { viewmodel.getNoteProducts() }.join()
        launch { viewmodel.initializeIsNoteSelectedList(viewmodel.noteProductState.value) }.join()
        //when: 이미 3개 수량 중에 3개를 모두 고른 경우
        viewmodel.handleNoteSelectData(
            index = 0,
            value = true,
            data = viewmodel.noteSelectDataState.value.get(0),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 0
        )
        viewmodel.handleNoteSelectData(
            index = 1,
            value = true,
            data = viewmodel.noteSelectDataState.value.get(1),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 1
        )
        viewmodel.handleNoteSelectData(
            index = 2,
            value = true,
            data = viewmodel.noteSelectDataState.value.get(2),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 2
        )
        val expectedNoteSelectData = mutableListOf<NoteSelect>()
        viewmodel.noteSelectDataState.value.map { expectedNoteSelectData.add(it) }
        expectedNoteSelectData.set(
            2,
            NoteSelect(
                productId = viewmodel.noteSelectDataState.value.get(2).productId,
                isSelected = false,
                nodeFaceIndex = null,
                isRecommended = viewmodel.noteSelectDataState.value.get(2).isRecommended
            )
        )
        //given: 1개를 취소하는 경우
        viewmodel.handleNoteSelectData(
            index = 2,
            value = false,
            data = viewmodel.noteSelectDataState.value.get(2),
            noteOrderQuantity = 3,
            selectedNotesOrderQuantity = 3
        )
        //then: 1개가 취소됨
        assertEquals(expectedNoteSelectData, viewmodel.noteSelectDataState.value)
    }

    @Test
    fun `test_changeSelectStateOfSingleNote_reflectInReturn`() = coroutineRule.runTest {
        val targetNote = fakeNoteSelectData.get(0)
        //given: targetNote 취소하기(isSelected = false)
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
        assertEquals(expectedResult, result)
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

        assertEquals(expectedDataAfterNodeSelected, resultAfter1NodeSelected)
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
        assertEquals(expectedDataAfterNodeSelected, resultAfter2NodeSelected)
    }
}