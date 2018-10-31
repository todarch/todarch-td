package contracts.gocli

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method GET()
    url '/non-secured/up'
  }

  response {
    status OK()
    headers {
      contentType textPlain()
    }
  }
}
