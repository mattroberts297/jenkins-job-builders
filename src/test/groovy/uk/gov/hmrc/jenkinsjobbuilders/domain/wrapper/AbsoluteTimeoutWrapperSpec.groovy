package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

@Mixin(JobParents)
class AbsoluteTimeoutWrapperSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withWrappers(AbsoluteTimeoutWrapper.timeoutWrapper(30))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            buildWrappers.'hudson.plugins.build__timeout.BuildTimeoutWrapper'.strategy[0].attribute('class') == 'hudson.plugins.build_timeout.impl.AbsoluteTimeOutStrategy'
            buildWrappers.'hudson.plugins.build__timeout.BuildTimeoutWrapper'.strategy.timeoutMinutes.text() == '30'
        }
    }

}
