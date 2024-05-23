package fastcompus.part1.chapter7

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*

Data Class : 데이터를 다루는데 최적화된 클래스
를 함수 안에 자동으로 생성함

작성방법(사용 시 주의점)
1. 기본생성자가 1개 이상의 매개변수(parameter)를 가짐
2. 모든 기본생성자의 매개변수는 val, var만 선언 가능
3. abstract, open, sealed, inner 사용불가

Data Class 안에 자동으로 생성되는 함수
- toString(), hasCode(), equals(), copy(), componentN()

 */

//@Parcelize : 객체를 쉽게 직렬화하여 Intent, Bundle를 통해 전달하는 어노테이션
//@Entity : DB 테이블을 정의하는 어노테이션
//@PrimaryKey : 테이블의 기본값을 지정하는 어노테이션

@Parcelize
@Entity(tableName = "voca")
data class VocaBook(
    val word: String,
    val mean: String,
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
): Parcelable
