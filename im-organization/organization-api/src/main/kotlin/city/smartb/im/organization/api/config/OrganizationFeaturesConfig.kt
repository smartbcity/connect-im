package city.smartb.im.organization.api.config

import city.smartb.im.organization.api.OrganizationMapperImpl
import city.smartb.im.organization.api.service.OrganizationAggregateServiceDefault
import city.smartb.im.organization.api.service.OrganizationFinderServiceDefault
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.organization.lib.service.OrganizationFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationFeaturesConfig {

    @Bean
    fun organizationFeatures(
        organizationFinderService: OrganizationFinderServiceDefault,
        organizationAggregateService: OrganizationAggregateServiceDefault,
    ) = OrganizationFeaturesImpl(
        organizationFinderService,
        organizationAggregateService,
        OrganizationMapperImpl()
    )
}

