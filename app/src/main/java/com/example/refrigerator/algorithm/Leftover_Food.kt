import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.refrigerator.IngredientData
import com.example.refrigerator.RecipeData
import com.example.refrigerator.algorithm.IngredientDataTransform
import java.text.ParseException
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class Leftover_Food internal constructor(
	dat1: ArrayList<IngredientData>,
	dat2: ArrayList<RecipeData>
) {
	var MAX_FOODS // 최대 음식 개수
			= 0
	var MAX_ING_PER_FOOD = 10 // 음식당 최대 재료 개수
	var today = Date().time / (1000 * 60 * 60 * 24) // 오늘 날짜 (일 수)
	var leftover = ArrayList<IngredientData>() // 입력받기용 (IngredientData.kt와 연동 가능)
	var foodList = ArrayList<RecipeData>() // 전체 음식의 종류들을 넣은 값 (RecipeData.kt와 연동 가능) <<
	var modLeft = ArrayList<IngredientDataTransform>() // 계산할 때 사용할 arrayList (leftover 변수 변형) <<
	var resultFood : ArrayList<RecipeData> = arrayListOf() // 최종적으로 return할 food 종류를 담은 array (우선순위 순서대로)
	lateinit var foodWeight : Array<LongArray> // foodList 위에 덮어쓰는 weight 계산용 clone


	init {
		leftover = dat1
		foodList = dat2
		println("데이터 입력 완료")
	}

	@RequiresApi(Build.VERSION_CODES.N)
	fun get_Leftover_Food(): ArrayList<RecipeData> {
		// 먼저 input 값을 아래 for문을 이용해서 분석, modLeft 함수에 추가
		MAX_FOODS = foodList.size
		foodWeight = Array(MAX_FOODS) { LongArray(MAX_ING_PER_FOOD + 3) }
		println("확인된 음식 종류 : $MAX_FOODS")
		// 추가로 들어가는 끝 3개의 index는 음식에 들어가는 총 재료의 개수, 현재 재료에 들어간 값, 가중치의 평균

		// leftover 입력을 modLeft의 계산하기 쉬운 형태로 변환.
		val dateForm = SimpleDateFormat("yyyy-MM-dd")
		for (i in leftover.indices) {
			val tmpIngData = IngredientDataTransform()
			tmpIngData.name = leftover[i].name
			// 숫자가 아닌 모든 단위값을 제거하여 plain integer값만 받음
			tmpIngData.amount = leftover[i].amount.replace("[^0-9]".toRegex(), "").toInt()
			try {
				tmpIngData.dateString = dateForm.parse(leftover[i].dateString).time
				tmpIngData.dateString /= (1000 * 60 * 60 * 24).toLong() // 날짜 값으로 변환
			} catch (e: ParseException) {
				println("Parse Failed")
				break
			}
			println("재료 데이터 추가 : " + tmpIngData.name + ", " + tmpIngData.amount + ", " + tmpIngData.dateString)
			modLeft.add(tmpIngData)
		}
		Collections.sort(modLeft, Ascending())
		for (i in modLeft.indices) {
			println("정돈된 재료 데이터 : " + modLeft[i].name + ", " + modLeft[i].amount + ", " + modLeft[i].dateString)
		}
		// 여기까지 왔으면 받은 정보를 modLeft 배열에 정돈된 형태로 들어가게 됨.

		// 여기는 foodList 입력을 기반으로 가중치를 계산하는 셀의 초기값을 지정
		for (i in 0 until MAX_FOODS) {
			foodWeight[i][MAX_ING_PER_FOOD + 2] = -1 // 가중치 평균 초기값 (추후 정렬 시 사용)
			foodWeight[i][MAX_ING_PER_FOOD + 1] =
				foodList[i].ingredient.size.toLong() // 각 음식 당 ingredient의 개수
			foodWeight[i][MAX_ING_PER_FOOD] = 0 // 현재 계산된 ingredient의 개수 (계산시 사용)
		}
		Calculate_Recipe()
		return resultFood
	}

	private fun Calculate_Recipe() {
		// 대략적인 방법은 그리디 알고리즘을 이용하여 유통기한이 낮은 순서대로 음식을 만듬.
		var calTime = 0 // 시간이 오래 걸릴때를 대비한 변수. 이 변수가 1억이 넘어가면 프로그램이 중지된다.
		for (i in modLeft.indices) { // 제료 array index
			if (calTime > 99999999) {
				println("Calculate limit Exceed")
				break
			}
			val curIngName = modLeft[i].name // 찾는대상 재료의 이름
			val curIngAmount = modLeft[i].amount // 찾는대상 재료의 양
			var curIngDate = modLeft[i].dateString - today // 오늘로부터 남은 유통기한
			if (curIngDate < 0) curIngDate = 0 // 가중치 64000
			if (curIngDate > 30) curIngDate = 31 // 가중치 729
			println("=============================================")
			println("현재 대상 재료 정보 | 이름 : $curIngName, 남은 날짜 : $curIngDate")
			println("=============================================")
			for (j in 0 until MAX_FOODS) { // 음식 array index
				// 이미 평균 weight가 계산된 음식이면 다음 음식으로 바로 이동
				if (foodWeight[j][MAX_ING_PER_FOOD + 2] != -1L) {
					calTime += 2
					continue
				}

				// 해당 음식에 선택된 재료가 있는 지 탐색
				println("현재 음식의 이름과 재료 종류 수 : " + foodList[j].name + ", " + foodList[j].ingredient.size)
				for (k in foodList[j].ingredient.indices) {
					val foodIng = foodList[j].ingredient[k].name // 탐색 대상 음식 재료
					val foodAmount =
						foodList[j].ingredient[k].amount.replace("[^0-9]".toRegex(), "")
							.toInt() // 탐색 대상 재료 필요양
					if (curIngName != foodIng) {
						calTime += 11
						continue
					} // 재료명이 다르면 다음거 탐색

					// 여기는 재료명이 같은 것을 찾았을 경우만 이 분기로 옴
					if (curIngAmount < foodAmount) {
						foodWeight[j][MAX_ING_PER_FOOD + 2] = -99
						calTime += 13
						break
					} // 양이 부족하면 음식제작이 불가능하다고 판단, 가중치를 음수로 지정하고 다음 음식을 탐색
					var weight = Math.pow((40 - curIngDate).toDouble(), 3.0)
						.toLong() // 남은 유통기한 일수에 따른 가중치 추가
					if (curIngAmount == foodAmount) { // 음식을 완벽하게 사용했을 경우
						weight *= 4
					} else { // 음식을 일부만 사용했을 경우
						var tmp = weight // 사용량에 따른 보너스
						tmp *= foodAmount.toLong()
						tmp /= curIngAmount.toLong()
						weight += tmp
					}
					var tmp = weight // 음식 종류 개수에 따른 보너스
					tmp *= foodList[j].ingredient.size.toLong()
					Log.d("checking","tmp : " + tmp.toString())
					tmp /= 10
					weight += tmp
					Log.d("checking","weight : " + weight.toString())
					println("\n\n================| ww |=================================================================")

					// 여기에 선호도 추가?

					// 해당 재료에 대한 가중치 넣기
					calTime += 20
					foodWeight[j][k] = weight // 가중치 대입
					var tlqkf = foodWeight[j][MAX_ING_PER_FOOD]
					tlqkf++;
					foodWeight[j][MAX_ING_PER_FOOD]= tlqkf // 계산된 재료 개수 +1
					Log.d("checking","foodWeight[j][k] : " + foodWeight[j][k].toString())
					println("\n\n================| ss |=================================================================")

					println("가중치 정보 추가됨 | 이름 : " + foodList[j].name + " | 가중치 : " + weight + " | 경과 시간 : " + calTime)
					Log.d("checkingdd","foodWeight? : " + foodWeight[j][MAX_ING_PER_FOOD].toString()+"ffff")
					Log.d("checkingdd","foodWeight???? : " + foodWeight[j][MAX_ING_PER_FOOD+1].toString()+"fddf")

					if (foodWeight[j][MAX_ING_PER_FOOD] == foodWeight[j][MAX_ING_PER_FOOD + 1]) {
						// 만약 해당 food 내의 모든 재료가 완성이 되었다면 평균 가중치 계산
						Log.d("checkingdd","foodWeight[j][k] : " + foodWeight[j][k].toString())

						// standard 방식을 채용
						var weightSum: Long = 0
						for (m in foodList[j].ingredient.indices) {
							weightSum += foodWeight[j][m]
							calTime += 2
						}
						foodWeight[j][MAX_ING_PER_FOOD + 2] =
							weightSum / foodList[j].ingredient.size
						calTime += 3
						Log.d("rank_wht","나는야가중치:  " + foodWeight[j][MAX_ING_PER_FOOD + 2] +"이름: "+foodList[j].name)

					}
					break
				} // k loop문의 끝
			} // j loop문의 끝
		} // i loop문의 끝
		println("\n\n================| 계산 완료 |================")

		// 여기까지 왔으면 모든 재료에 대한 데이터 (또는 유통기한이 임박한 재료들만의 음식 리스트 완료)
		// 5순위까지의 음식 데이터를 넣어둠.
		val foodRank = Array(3) {
			LongArray(
				2
			)
		} // 각각 음식 index번호, weight값
		for (i in 0..2) {
			foodRank[i][0] = -1
			foodRank[i][1] = -1
		}
		for (i in 0 until MAX_FOODS) {
			val calWeight = foodWeight[i][MAX_ING_PER_FOOD + 2]
			println("index " + i + " | 음식명 : " + foodList[i].name + " | 평균 가중치 : " + calWeight)
			if (calWeight < 100) continue  // 만들 수 없거나 스킵된 음식 제작법 거르기

			// top 3에 들면 3위 갈아치우기
			if (foodRank[2][1] < calWeight) {
				foodRank[2][0] = i.toLong()
				foodRank[2][1] = calWeight
			}
			// 이후 상위 랭킹보다 갈아치운 값이 더 크면 계속 스왑해줌
			for (j in 1 downTo 0) {
				if (foodRank[j][1] < foodRank[j + 1][1]) {
					val tmp1 = foodRank[j][0]
					val tmp2 = foodRank[j][1]
					foodRank[j][0] = foodRank[j + 1][0]
					foodRank[j][1] = foodRank[j + 1][1]
					foodRank[j + 1][0] = tmp1
					foodRank[j + 1][1] = tmp2
				} else break
			}
		}

		// 여기까지 왔으면 음식 리스트에 대한 순위까지 정산 완료
		for (i in 0..2) {
			Log.d("whywhy","Rank")
			// foodRank에 있는 index 순서대로 resultFood에 입력 (총 5순위까지)
			if (foodRank[i][0] < 0) {
				//out of index
				break
			}
			resultFood.add(foodList[foodRank[i][0].toInt()])
			Log.d("whywhy","Rank " + (i + 1).toString() + " : " + foodList[foodRank[i][0].toInt()].name + ", " + foodRank[i][1].toString())
			println("Rank " + (i + 1).toString() + " : " + foodList[foodRank[i][0].toInt()].name + ", " + foodRank[i][1].toString())
			// rank n : (이름), (평균가중치)
		}
		//Log.d("whywhy","Rank  : " + foodList[foodRank[1][0].toInt()].name + ", " + foodRank[1][1].toString())

	}

	internal inner class Ascending :
		Comparator<IngredientDataTransform?> {
		override fun compare(p0: IngredientDataTransform?, p1: IngredientDataTransform?): Int {
			if (p0?.dateString!! > p1?.dateString!!) return 1
			else if (p0?.dateString!! < p1?.dateString!!) return -1
			return 0
		}
	}
}

