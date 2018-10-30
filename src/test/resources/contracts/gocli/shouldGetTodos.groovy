import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method GET()
    url '/api/todos/1'
    headers {
      contentType applicationJson()
    }
  }
  response {
    status 200
    body """
    {
      "createdAtEpoch": 0,
      "description": "string",
      "doneDateEpoch": 0,
      "id": 0,
      "priority": 0,
      "status": "string",
      "timeNeededInMin": 0,
      "title": "string",
      "userId": 0
    }
    """
    headers {
      contentType applicationJson()
    }
  }
}
