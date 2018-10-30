package contracts.gocli

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'GET'
    url '/non-secured/up'
    headers {
      contentType applicationJson()
    }
  }

  response {
    status 200
    headers {
      contentType applicationJson()
    }
  }
}
