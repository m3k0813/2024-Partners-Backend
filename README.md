# ✂️ URL Shortener 앱 ✂️

---

<br>

## 안내사항

- 본 레포지토리를 fork하여 과제를 수행하고, 완료시 PR을 보냅니다.
- 과제의 소스코드는 본인의 GitHub 레포지토리에 **Public**으로 올려주세요.
- 진행 간 문의사항은 이 레포지토리의 Issue로 등록해주세요.
- 구현 내용은 [README.md](http://readme.md/) 하단에 이어서 작성해 주세요.

<br>

## 기본 요구사항

- 아래 제시된 라이브러리, 프레임워크를 활용하여 URL Shortener 앱을 구현합니다.
- 일관된 코딩 컨벤션을 유지해주세요.
- 기능 당 커밋은 필수입니다.

<br>

## 기술 스택별 요구사항

### 1. SpringBoot + Java

- 빌드 도구는 gradle를 사용해주세요.
- Spring data JPA를 사용해주세요.

### 2. Python + Django

- 데이터베이스는 db.sqlite3를 사용해주세요.

<br>

---

# 기능

### 1. Short Link 생성하기

- 원본 URL, 줄여진 URL, 해시값, 생성일자를 포함해야합니다.
- 원본 URL 해시값을 엔드포인트 뒤에 붙여 줄여진 URL을 생성합니다.
    - 줄여진 URL 형식 예시: [http://localhost:8000/short-links/{hash}](http://localhost:8000/short-links/)
- 원본 URL로 만든 hash 값이 데이터베이스에 존재하면 저장된 그 데이터를 반환합니다.

<br>

### 2. Short Link 리다이렉트하기

- 줄여진 URL으로 요청을 보냈을 때, 원본 URL로 Redirect 되어야 합니다.

<br> 

### 3. 전체 Links 조회하기

- 원본 URL, 줄여진 URL, 해시값과 같은 정보를 반환합니다.
- 최근 생성일자 순으로 정렬합니다.
- 삭제 처리된 링크의 정보는 조회하지 않도록 합니다.

<br>

### 4. Short Link 삭제하기

- Soft Delete로 구현해야합니다.
- 조회 기능에서 삭제 처리된 URL은 조회하지 않도록 합니다.
- Redirect 기능에서 삭제 처리된 URL은 접속되지 않도록 합니다.

<br> <br>

## [📃 Swagger 문서화]

- Swagger 문서화 코드를 작성합니다. (추가기능)

<br><br>

---

# 기여해주신 분

- [백한결](https://github.com/baekhangyeol) 👾
- [조희은](https://github.com/kubit2) 👾

---

### 전체 구조
<img width="387" alt="스크린샷 2024-05-24 오전 1 28 55" src="https://github.com/m3k0813/m3k0813/assets/41982054/52ec7036-018b-4bfb-8df5-7eb4c6636947">

## 1. Shork Link 생성하기
 </br><img width="706" alt="스크린샷 2024-05-24 오전 1 30 08" src="https://github.com/m3k0813/m3k0813/assets/41982054/1144e718-6010-49b0-8bfd-516ee36b8d35">
 
Shork Link를 생성하기 위해 Controller를 통해 json으로 originalUrl을 requestDto에 담아 Service로 요청을 받는다. 

#### Base62

```
public class Base62 {
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (value % 62);
            sb.append(BASE62.charAt(i));
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }

    public static String encode(BigInteger value) {
        StringBuilder sb = new StringBuilder();
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = value.divideAndRemainder(BigInteger.valueOf(62));
            sb.append(BASE62.charAt(divmod[1].intValue()));
            value = divmod[0];
        }
        return sb.toString();
    }

    public static long decode(String value) {
        long result = 0L;
        long power = 1L;
        for (int i = 0; i < value.length(); i++) {
            result += BASE62.indexOf(value.charAt(i)) * power;
            power *= 62;
        }
        return result;
    }
}
```

</br>
Service에서 UUID를 통해 고유 식별자를 랜덤으로 생성하고 Base62 알고리즘으로 새로운 해시값을 만들어서 http://localhost:8000/short-links/{hash} 형태로 저장한다.
</br>
</br>

#### Postman 결과
<img width="926" alt="스크린샷 2024-05-24 오전 1 24 09" src="https://github.com/m3k0813/m3k0813/assets/41982054/993b3ba8-e9a5-4a70-aa19-76a99c681404">

</br>
</br>

### 2. Short Link 리다이렉트
<img width="916" alt="스크린샷 2024-05-24 오전 1 26 24" src="https://github.com/m3k0813/m3k0813/assets/41982054/2230ec4a-4990-4da3-ba7c-316f93cdf4bf">

</br>
</br>
</br>

### 3. Short Links 조회하기

<img width="908" alt="스크린샷 2024-05-24 오전 1 24 39" src="https://github.com/m3k0813/m3k0813/assets/41982054/58d5cc64-4373-41d8-9266-b68b41aee296">
</br>
원본 URL, 줄여진 URL, 해시값을 최신 순으로 정렬하여 조회
</br>
</br>
</br>

### 4. Short Link 삭제하기
<img width="1253" alt="스크린샷 2024-05-24 오전 1 25 41" src="https://github.com/m3k0813/m3k0813/assets/41982054/a27aedc7-3be3-43d3-bb10-f5db065c26ad">

</br>
deleted 필드를 추가하여 Soft Delete 구현
</br>
</br>
</br>

### Swagger 문서화

<img width="1478" alt="스크린샷 2024-05-24 오전 1 26 48" src="https://github.com/m3k0813/m3k0813/assets/41982054/6cf0fb3a-53a9-423a-991e-11ca40cc02c9">
