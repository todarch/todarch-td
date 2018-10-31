package contracts.gocli

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method DELETE()
    url '/api/todos/63'
    headers {
      contentType applicationJsonUtf8()
      header 'Authorization': execute('getJwt()')
    }
  }
  response {
    status NO_CONTENT()
  }
}
