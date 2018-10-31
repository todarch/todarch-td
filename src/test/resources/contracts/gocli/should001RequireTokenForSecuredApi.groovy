package contracts.gocli

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method GET()
    url '/api/todos/5'
  }

  response {
    status FORBIDDEN()
  }
}
