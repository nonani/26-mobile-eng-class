package com.jongchan.androidacrhi.search.data

/**
 * `v2/search/page` 목(mock) 응답.
 *
 * 실제 서버 엔드포인트가 아직 동작하지 않아, [SearchPageDataSource] 가 네트워크 대신 이 문자열을
 * 파싱하도록 대체한다. 서버가 준비되면 [SearchPageDataSource.getSearchPage] 의 실제 호출로 복구하고
 * 이 파일을 삭제하면 된다.
 *
 * 주의: 주입되는 Json 인스턴스는 주석(`//`)을 허용하지 않으므로, 원본 스펙에 있던
 * `actionType` 주석은 제거한 순수 JSON 만 담는다.
 */
internal object SearchPageMockData {
    val SEARCH_PAGE_JSON: String = """
        {
          "sduiComponents" : [
            {
              "viewType" : "TitleDescriptionViewType",
              "content" : {
                "titleText" : "Title",
                "descriptionText" : "This is Description Text for type1"
              }
            },
            {
              "viewType" : "StartImageTitleDescriptionActionIconType",
              "content" : {
                "titleText" : "Title",
                "descriptionText" : "This is Description Text for type2",
                "image" : {
                  "imgUrl" : "https://aiswmaestro.org/2x/smile-icon.png",
                  "size" : {
                    "width" : 20,
                    "height" : 20
                  },
                  "bgColor" : "#232323"
                },
                "actionIcon" : {
                  "imgUrl" : "https://aiswmaestro.org/2x/smile-icon.png",
                  "size" : {
                    "width" : 20,
                    "height" : 20
                  },
                  "action" : {
                    "navigateLink" : "https://aiswmaestro.org/page2",
                    "actionType" : "navigate"
                  }
                }
              }
            },
            {
              "viewType" : "TitleDescriptionEndImageViewType",
              "content" : {
                "titleText" : "Title",
                "descriptionText" : "This is Description Text for type2",
                "endImage" : {
                  "imgUrl" : "https://aiswmaestro.org/2x/smile-icon.png",
                  "size" : {
                    "width" : 16,
                    "height" : 16
                  },
                  "bgColor" : "#232323"
                }
              }
            }
          ]
        }
    """.trimIndent()
}
