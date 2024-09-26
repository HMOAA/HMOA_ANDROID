package com.hmoa.core_domain.usecase

import javax.inject.Inject

class CalculateMinAndMaxPriceOutOfStringUseCase @Inject constructor() {
    suspend operator fun invoke(priceRange: String): Pair<Int, Int> {
        return when {
            // "50,000원 이하" 같은 패턴 처리
            priceRange.contains("이하") -> {
                val maxPrice = priceRange.filter { it.isDigit() }.toInt()
                Pair(0, maxPrice) // 최소값 없음, 최대값만 있음
            }
            // "100,000원 이상" 같은 패턴 처리
            priceRange.contains("이상") -> {
                val minPrice = priceRange.filter { it.isDigit() }.toInt()
                Pair(minPrice, 0) // 최소값만 있음, 최대값 없음
            }
            // "50,000 ~ 100,000원" 같은 패턴 처리
            priceRange.contains("~") -> {
                val prices = priceRange.split("~").map { it.filter { char -> char.isDigit() }.toInt() }
                Pair(prices[0], prices[1]) // 최소값과 최대값 모두 있음
            }

            else -> Pair(0, 0) // 해당 사항이 없으면 null 반환
        }
    }
}