# 버드뷰(화해) Android 과제
 
### 스크린샷

<img src="https://github.com/lkw1120/birdview-hwahae-android/blob/master/assets/images/Screenshot_2020-01-28-01-41-03.png" width="35%"><img src="https://github.com/lkw1120/birdview-hwahae-android/blob/master/assets/images/Screenshot_2020-01-28-01-41-21.png" width="35%">
<img src="https://github.com/lkw1120/birdview-hwahae-android/blob/master/assets/images/Screenshot_2020-01-28-01-41-25.png" width="35%"><img src="https://github.com/lkw1120/birdview-hwahae-android/blob/master/assets/images/Screenshot_2020-01-28-02-43-48.png" width="35%">

### 요구 사항

- 상품 목록
  + 스크롤에 따라 자동 로드 되어야합니다. (20개씩) ✔
  + 상품을 터치하면 해당 상품의 detail view로 전환됩니다. ✔
  + 목록 아래로 이동할 때 사라지고, 위로 이동할 때 나타나는 헤더를 구현하세요. ✔
    * 정확한 동작 방식은 아래의 프로토타입에서 확인하세요. ✔
    * 헤더의 필터를 선택할 경우, 선택한 피부 타입에 대한 점수순으로 정렬되어야 합니다. ✔
    * 선택 창은 OS에서 제공하는 기본 선택창을 이용해 주세요. ✔
  + 검색 창에 키워드를 입력하여 상품을 검색할 수 있습니다. ✔
- 상품 상세 화면
  + 뒤로가기 버튼 클릭 시 상품 목록으로 돌아갈 수 있습니다. ✔

### 사용한 라이브러리

- Foundation
  + AppCompat
  + Android KTX
- Architecture
  + DataBinding
  + ViewModel
  + LiveData
- UI
  + Layout
  + Animation
- Third party
  + Retrofit2
  + Glide

### 참조

- [github.com/android/architecture-components-samples](https://github.com/android/architecture-components-samples)
- [github.com/android/sunflower](https://github.com/android/sunflower)
