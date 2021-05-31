package com.example.demo.utility;

import org.springframework.stereotype.Component;

/**
 * よく使いそうな周りの共通処理クラス
 */
@Component
public class CommonUtility {
	/**
	 * 2つの値から小さいほうの値を返す
	 * @param value1
	 * @param value2
	 * @return
	 */
	public Integer min(Integer value1, Integer value2) {
		if(value1 < value2)
			return value1;
		else
			return value2;
	}
}
