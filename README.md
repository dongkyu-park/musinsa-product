# 상품 관리 및 가격 조회 서비스

API 문서 : https://documenter.getpostman.com/view/20747799/UzQvt5Av

상품 CRUD 기능 추가 및 서비스 이용자는 아래의 8가지 카테고리에서 상품을 1개씩 선택해 코디를 완성하여 구매 하고자 한다.

- 상의
- 아우터
- 바지
- 스니커즈
- 가방
- 모자
- 양말
- 액세서리

## 기능 요구사항

조회 시 아래 3가지 상황을 만족 한다고 가정한다.
- 상품 품절 X
- 추가 비용 X
- 각 브랜드는 하나의 카테고리 별 최소 1개의 상품은 존재

---

- [x] 브랜드의 상품, 가격을 추가할 수 있어야 한다.
  - 요청의 필수 입력 값은 브랜드명, 카테고리명, 가격
    - 브랜드명 입력 길이 제한, 카테고리명 유효성 체크, 가격 음수 X
- [x] 8개 카테고리의 상품을 브랜드 별로 자유롭게 선택해서, 모든 상품을 구매할 때 최저가를 조회하여 정보를 반환해야 한다.
  - 요청의 필수 입력 값은 브랜드명, 카테고리명 8개
    - 브랜드명 입력 길이 제한, 카테고리명 8개 유효성 체크
  - 반환값은 각 카테고리명, 브랜드명, 가격, 가격 총합
- [x] 모든 카테고리의 상품을 1개의 브랜드에서 구매할 경우, 해당 브랜드의 카테고리별 최저가 상품을 조회하여 반환해야 한다.
  - 요청의 필수 입력 값은 브랜드명
    - 브랜드명 입력 길이 제한
  - 반환값은 브랜드명, 가격 총합
- [x] 카테고리명으로 해당 카테고리 상품의 최소, 최대 가격을 조회할 수 있어야 한다.
  - 요청의 필수 입력 값은 카테고리명
    - 카테고리명 유효성 체크
  - 반환값은 최소 가격의 브랜드명, 가격 // 최대 가격의 브랜드명, 가격
- [ ] 상품 정보를 수정할 수 있어야 한다.
  - 요청의 필수 입력 값은 상품id
- [ ] 상품을 삭제할 수 있어야 한다.
  - 요청의 필수 입력 값은 상품id

## 프로그래밍 요구사항

- 저장소를 사용하여 저장하되, 저장소는 언제든 변경이 가능하게끔 구현한다.
- 요청에 빠르게 응답 할 수 있는 방법을 고려하여 구현한다.
  ![image](https://user-images.githubusercontent.com/81552729/180725245-85305dd8-d471-4d2a-afc2-ff2753091316.png)
  - Statistic 객체  
    이미 조회 된 최저가, 최고가를 저장하는 캐시 저장소 개념으로 사용 했습니다.
    > BrandProductStatistic 는 BrandCategoryTag 를 필드로 갖는다.
    CategoryProductStatistic CategoryProductTag 를 필드로 갖는다.
  - Tag 객체  
    Key, Value 쌍의 Map을 필드로 가지며, Key와 Value 끼리 연관 관계를 갖습니다.
    > BrandCategoryTag 는 Map<String, CategoryProductTag> 를 필드로 갖는다.  
    CategoryProductTag 는 Map<Category, ProductInfo> 를 필드로 갖는다.  
    ProductInfo 는 상품의 정보 객체로, id, category, brand, price 를 필드로 갖는다.

  - 1. [Brand, Category] 선택 조회 시 BrandProductStatistic 객체의 BrandCategoryTag 를 이용하여 최저가 조회
  - 2. [Brand] 선택 조회 시 BrandProductStatistic 객체의 BrandCategoryTag 를 이용하여 모든 카테고리의 최저가 조회
  - 3. [Category] 선택 조회 시 CategoryProductStatistic 객체의 CategoryProductTag 를 이용하여 카테고리별 최저, 최고가 조회

  > Service 레이어에서 Repository 레이어를 호출하기 전에 캐시를 먼저 찾은 뒤  
  > - 캐시가 있다면, DB에 접근하지 않고 저장 된 캐시값을 반환 
  > - 캐시가 없다면, DB에 접근하여 데이터를 가져오고 캐시에 저장

- API 응답이 실패 할 경우, 실패값과 실패 사유를 전달한다.
