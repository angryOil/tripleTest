# tripleTest

## 실행방법
>> src/main/resource에있는 ddl.sql 파일을 mysql로 실행 시킵니다. 사용할 db 이름은 (triple_test)입니다. <br/>
>> 그후스프링app을 실행시킵니다.<br/>
>> postman등 API요청 프로그램 으로 localhost:8080/ 이후 아래에있는 내용들을 사용하면됩니다.
<hr/>
USER
post	/user/new  (회원가입)

	요청{
		"id":"name",
		"pw":"pw"
	}

	응답{
    		"uuid": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
    		"id": "112",
    		"pw": "world"
		}


get /users  (유저 리스트확인)

	응답
	[
    	{
    	    "uuid": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
    	    "id": "112",
    	    "pw": "world"
    	}
    ]

get /point/{userId}{가입할떄작성한 }  (특정 유저  포인트 확인하기)

	요청 (회원가입때 사용한 id){
    112}
  
	응답 
	{
    	"uuid": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
    	"userId": "112",
    	"mileage": 0
	}


post /place (장소 추가  특정장소 지정하고싶다면 special_flag 를 true로 만들고 요청하면됩니다 (첫 리뷰어 +1))

	요청		
		{
		    "location":"의정부",
		    "name":"스타밥스",
		    "special_flag": false (이 플레그 유무에 따라 특정 지역 설정 )
		}

	응답
		{
		    "uuid": "eb3610ff-30d1-4792-9564-ba559008ff20",
		    "location": "의정부",
		    "name": "스타밥스",
		    "specialFlag": false
		}


post /events (이벤트(리뷰 작성 여기서 content , photoDtos는 반드시 필요한값은 아닙니다.)

	요청
		{
		  
		    "userId" : "b793f46f-28df-41c1-9d6e-c38f8b89d553",
		    "placeId" : "eb3610ff-30d1-4792-9564-ba559008ff20",
		    "content":"여기가 특별한 장소입니까?",
		    "photoDtos":
		        [
		            {"photoName":"사진1"},
		            {"photoName":"사진2"},
		            {"photoName":"사진3"}
		        ] 
		}

	응답  (content와 photoDtos 배열에 값이 없거나 없이 요청시 각 -1 점 (최소 점수 0점))
		{
		    "reviewId": c0dd62b3-7f2a-40a4-b5c5-896340bce74b,
		    "userId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
		    "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
		    "content": "여기가 특별한 장소입니까?",
		    "photoDtos": [
		        {
		            "photoID": "c0dd62b3-7f2a-40a4-b5c5-896340bce74b",
		            "reviewID": "eb3610ff-30d1-4792-9564-ba559008ff20",
		            "photoName": "사진1"
		        },
		        {
		            "photoID": "e6ba0d68-1320-4cf0-b93c-5ce41c15714d",
		            "reviewID": "eb3610ff-30d1-4792-9564-ba559008ff20",
		            "photoName": "사진2"
		        },
		        {
		            "photoID": "d98bd8f6-22a9-43b2-9b61-dffad2646fad",
		            "reviewID": "eb3610ff-30d1-4792-9564-ba559008ff20",
		            "photoName": "사진3"
		        }
		    ]
		}


get /events (모든 이벤트 보여주기)

		응답
				[
		    {
		        "reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
		        "userId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
		        "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
		        "content": "여기가 특별한 장소입니까?",
		        "photoDtos": [
		            {
		                "photoID": "55dd4d0d-fb78-4ce5-a0cf-1277fafbf1b0",
		                "reviewID": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
		                "photoName": "사진2"
		            },
		            {
		                "photoID": "57e0eac4-7c3e-4f78-aba6-5b462de6e7d2",
		                "reviewID": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
		                "photoName": "사진1"
		            },
		            {
		                "photoID": "8dc501c3-7304-4ec2-a593-d4f56a2c30e7",
		                "reviewID": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
		                "photoName": "사진3"
		            }
		        ]
		    }
		]


get /events/{reviewId} {리뷰의 uuid}

	응답
		{
		    "reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
		    "userId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
		    "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
		    "content": "여기가 특별한 장소입니까?",
		    "photoDtos": [
		        {
		            "photoID": "55dd4d0d-fb78-4ce5-a0cf-1277fafbf1b0",
		            "reviewID": null,
		            "photoName": "사진2"
		        },
		        {
		            "photoID": "57e0eac4-7c3e-4f78-aba6-5b462de6e7d2",
		            "reviewID": null,
		            "photoName": "사진1"
		        },
		        {
		            "photoID": "8dc501c3-7304-4ec2-a593-d4f56a2c30e7",
		            "reviewID": null,
		            "photoName": "사진3"
		        }
		    ]
		}

patch /events

	요청 	
	    {
	        "userId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
			"placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
	        "content":"내용수정 ?",
	        "photoDtos":
	            [
	                {"photoName":"사진1"},
	                {"photoName":"사진2"},
	                {"photoName":"사진3"}
	            ] 
	    }

	    응답
	    	{
			    "reviewId": null,
			    "userId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
			    "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
			    "content": "내용수정 ?",
			    "photoDtos": [
			        {
			            "photoID": null,
			            "reviewID": null,
			            "photoName": "사진1"
			        },
			        {
			            "photoID": null,
			            "reviewID": null,
			            "photoName": "사진2"
			        },
			        {
			            "photoID": null,
			            "reviewID": null,
			            "photoName": "사진3"
			        }
			    ]
			}


delete /events

		요청
			{
				"reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5"
			}

		응답
		{
		    "uuid": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
		    "userId": "112",
		    "mileage": 9
		}
    
    logs

	get /logs (모든 포인트 이력을 보여줍니다)

	응답

	[
		    {
		        "reviewId": "be7a30c2-db99-4ff7-a22c-24f668b2ad3a",
		        "userId": "99a71ed1-fe4c-4185-98a6-d5bd105ba1be",
		        "placeId": "6b162ec9-121f-4d3e-b7bc-cb79ef2a09c0",
		        "deleteFlag": false,
		        "content": "여기가 특별한 장소입니까?",
		        "photoDtos": [
		            {
		                "photoID": "0bb496fd-5e80-4a9a-81aa-51e3ffc63470",
		                "reviewID": "be7a30c2-db99-4ff7-a22c-24f668b2ad3a",
		                "photoName": "사진3"
		            },
		            {
		                "photoID": "78839813-6fd4-4c7e-975e-323200ce556b",
		                "reviewID": "be7a30c2-db99-4ff7-a22c-24f668b2ad3a",
		                "photoName": "사진2"
		            },
		            {
		                "photoID": "d3cbb72b-840b-4516-b6dc-bbe508c9eda8",
		                "reviewID": "be7a30c2-db99-4ff7-a22c-24f668b2ad3a",
		                "photoName": "사진1"
		            }
		        ]
		    }
		]



	get /logs/{userUUID}  (특정 유저의 기록을 보여줍니다 )

	응답
	[
    {
        "uuid": "0fe729ad-ff4f-4cfb-9248-583af16db8ba",
        "pointId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
        "reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
        "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
        "action": "MOD",
        "type": "REVIEW",
        "pointApply": 0
    },
    {
        "uuid": "222d7567-e6b3-4f55-be65-9c69f539cf7a",
        "pointId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
        "reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
        "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
        "action": "ADD",
        "type": "REVIEW",
        "pointApply": 2
    },
    {
        "uuid": "c707b523-10a3-48ad-baff-7345eb126717",
        "pointId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
        "reviewId": "701aa63e-b94b-4f8c-95dc-0c5456b169d9",
        "placeId": "9e0a8aac-1a20-4643-9c80-44f928f6b87d",
        "action": "ADD",
        "type": "REVIEW",
        "pointApply": 3
    },
    {
        "uuid": "d7db055f-d683-449b-ba4c-7c5b39fb156c",
        "pointId": "b793f46f-28df-41c1-9d6e-c38f8b89d553",
        "reviewId": "afe7fa47-1d28-4079-b75c-1ac3a3e11dc5",
        "placeId": "eb3610ff-30d1-4792-9564-ba559008ff20",
        "action": "DELETE",
        "type": "REVIEW",
        "pointApply": -2
    }
]
