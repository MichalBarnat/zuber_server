package com.bbc.zuber.service

import com.bbc.zuber.model.googledistancematrix.DistanceMatrixDistance
import com.bbc.zuber.model.googledistancematrix.DistanceMatrixDuration
import com.bbc.zuber.model.googledistancematrix.DistanceMatrixElement
import com.bbc.zuber.model.googledistancematrix.DistanceMatrixResponse
import com.bbc.zuber.model.googledistancematrix.DistanceMatrixRow
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class GoogleDistanceMatrixServiceTest extends Specification {

    RestTemplate restTemplate = Mock(RestTemplate)
    GoogleDistanceMatrixService service = new GoogleDistanceMatrixService(restTemplate)

    def "should return correct distance String from API"() {
        given:
        DistanceMatrixResponse mockResponse = new DistanceMatrixResponse(status: "OK", rows: [
                new DistanceMatrixRow(elements: [new DistanceMatrixElement(distance: new DistanceMatrixDistance(text: "10 km", value: 10000), status: "OK")])
        ])
        restTemplate.getForObject(_, DistanceMatrixResponse.class) >> mockResponse

        when:
        def result = service.getDistanceString("from", "to")

        then:
        result == "10 km"
    }

    def "should return correct distance Integer from API"() {
        given:
        DistanceMatrixResponse mockResponse = new DistanceMatrixResponse(status: "OK", rows: [
                new DistanceMatrixRow(elements: [new DistanceMatrixElement(distance: new DistanceMatrixDistance(text: "10 km", value: 10005), status: "OK")])
        ])
        restTemplate.getForObject(_, DistanceMatrixResponse.class) >> mockResponse

        when:
        def result = service.getDistanceInt("from", "to")

        then:
        result == 10005
    }

    def "should return correct duration String from API"() {
        given:
        DistanceMatrixResponse mockResponse = new DistanceMatrixResponse(status: "OK", rows: [
                new DistanceMatrixRow(elements: [new DistanceMatrixElement(duration: new DistanceMatrixDuration(text: "3 godz. 25 min", value: 12280), status: "OK")])
        ])
        restTemplate.getForObject(_, DistanceMatrixResponse.class) >> mockResponse

        when:
        def result = service.getDurationString("from", "to")

        then:
        result == "3 godz. 25 min"
    }

    def "should return correct duration Integer from API"() {
        given:
        DistanceMatrixResponse mockResponse = new DistanceMatrixResponse(status: "OK", rows: [
                new DistanceMatrixRow(elements: [new DistanceMatrixElement(duration: new DistanceMatrixDuration(text: "3 godz. 25 min", value: 12280), status: "OK")])
        ])
        restTemplate.getForObject(_, DistanceMatrixResponse.class) >> mockResponse

        when:
        def result = service.getDurationInt("from", "to")

        then:
        result == 12280
    }

    def "should handle wrong query from API"() {
        given:
        DistanceMatrixResponse mockResponse = new DistanceMatrixResponse(status: "OK", rows: [
                new DistanceMatrixRow(elements: [new DistanceMatrixElement(status: "NOT_FOUND")])
        ])
        restTemplate.getForObject(_, DistanceMatrixResponse.class) >> mockResponse

        when:
        def result = service.getDistanceString("from", "to")

        then:
        result == null
    }

}
