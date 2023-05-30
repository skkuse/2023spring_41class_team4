# Develop Log
개발 과정에서 에러 해결이나 이슈, 관련 개념 등이 있으면 적어보겠습니다. 공통적인 문제인 경우 시간을 아낄 수 있으니 공유합시다!! (-충현)

# Errors
## 001 : next
next.js를 설치했는데, `npm run dev` 하면 아래 오류가 뜨면서 실행이 안됐습니다.  
```
'next'은(는) 내부 또는 외부 명령, 실행할 수 있는 프로그램, 또는배치 파일이 아닙니다.
``` 
원인은 `npm install`에서 생성되는 `package-lock.json` 파일의 의존성 때문입니다. MAC와 Linux에서는 이런 문제 없이 잘 작동한다고 하네요. \
만약 Window 환경에서 저 문제가 발생하면 `package-lock.json` 파일을 삭제하고, 루트폴더(client)에서 다시 `npm install` 한 후, `npm run dev` 하면 잘 실행됩니다. 관련 포스팅은 [여기](https://britny-no.tistory.com/73)

## 002 : paginate 
paginate 이 install 되어있지 않아서 compile error가 뜨는 경우
```
npm install react-paginate
```

## 003 : CORS
fetch로 백엔드 서버에서 값을 받아올 때 cors 정책으로 인한 에러가 떴습니다. 참고한 포스팅은 [여기](https://happyguy81.tistory.com/188)