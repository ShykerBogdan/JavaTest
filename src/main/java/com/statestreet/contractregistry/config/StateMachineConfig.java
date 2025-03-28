package com.statestreet.contractregistry.config;

import com.statestreet.contractregistry.statemachine.DeploymentEvent;
import com.statestreet.contractregistry.statemachine.DeploymentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<DeploymentState, DeploymentEvent> {

    private static final Logger log = LoggerFactory.getLogger(StateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<DeploymentState, DeploymentEvent> states) throws Exception {
        states.withStates()
                .initial(DeploymentState.INITIAL)
                .states(EnumSet.allOf(DeploymentState.class))
                .end(DeploymentState.COMPLETED)
                .end(DeploymentState.ERROR);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DeploymentState, DeploymentEvent> transitions) throws Exception {
        transitions
                // Authentication transitions
                .withExternal()
                .source(DeploymentState.INITIAL)
                .target(DeploymentState.AUTHENTICATED)
                .event(DeploymentEvent.AUTHENTICATION_SUCCESS)
                .and()
                
                // Deployment request transitions
                .withExternal()
                .source(DeploymentState.AUTHENTICATED)
                .target(DeploymentState.DEPLOY_REQUESTED)
                .event(DeploymentEvent.DEPLOYMENT_REQUEST_SUCCESS)
                .and()
                
                // Approval request transitions
                .withExternal()
                .source(DeploymentState.DEPLOY_REQUESTED)
                .target(DeploymentState.APPROVAL_PENDING)
                .event(DeploymentEvent.REQUEST_APPROVAL)
                .and()
                
                // Hash retrieval transitions
                .withExternal()
                .source(DeploymentState.APPROVAL_PENDING)
                .target(DeploymentState.HASH_RETRIEVED)
                .event(DeploymentEvent.HASH_FETCHED)
                .and()
                
                // Hash signing transitions
                .withExternal()
                .source(DeploymentState.HASH_RETRIEVED)
                .target(DeploymentState.HASH_SIGNED)
                .event(DeploymentEvent.HASH_SIGNED)
                .and()
                
                // Deployment approval transitions
                .withExternal()
                .source(DeploymentState.HASH_SIGNED)
                .target(DeploymentState.DEPLOYMENT_APPROVED)
                .event(DeploymentEvent.DEPLOYMENT_APPROVED)
                .and()
                
                // Deployment completion transitions
                .withExternal()
                .source(DeploymentState.DEPLOYMENT_APPROVED)
                .target(DeploymentState.DEPLOYED)
                .event(DeploymentEvent.DEPLOYMENT_COMPLETED)
                .and()
                
                // Whitelist request transitions
                .withExternal()
                .source(DeploymentState.DEPLOYED)
                .target(DeploymentState.WHITELIST_REQUESTED)
                .event(DeploymentEvent.REQUEST_WHITELIST)
                .and()
                
                // Whitelist hash retrieval transitions
                .withExternal()
                .source(DeploymentState.WHITELIST_REQUESTED)
                .target(DeploymentState.WHITELIST_HASH_RETRIEVED)
                .event(DeploymentEvent.WHITELIST_HASH_FETCHED)
                .and()
                
                // Whitelist hash signing transitions
                .withExternal()
                .source(DeploymentState.WHITELIST_HASH_RETRIEVED)
                .target(DeploymentState.WHITELIST_HASH_SIGNED)
                .event(DeploymentEvent.WHITELIST_HASH_SIGNED)
                .and()
                
                // Whitelist approval transitions
                .withExternal()
                .source(DeploymentState.WHITELIST_HASH_SIGNED)
                .target(DeploymentState.WHITELIST_APPROVED)
                .event(DeploymentEvent.WHITELIST_APPROVED)
                .and()
                
                // Token registration transitions
                .withExternal()
                .source(DeploymentState.WHITELIST_APPROVED)
                .target(DeploymentState.TOKEN_REGISTERED)
                .event(DeploymentEvent.TOKEN_REGISTERED)
                .and()
                
                // Completion transitions
                .withExternal()
                .source(DeploymentState.TOKEN_REGISTERED)
                .target(DeploymentState.COMPLETED)
                .event(DeploymentEvent.REGISTER_TOKEN)
                .and()
                
                // Error transitions from any state
                .withExternal()
                .source(DeploymentState.INITIAL)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.AUTHENTICATED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.DEPLOY_REQUESTED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.APPROVAL_PENDING)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.HASH_RETRIEVED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.HASH_SIGNED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.DEPLOYMENT_APPROVED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.DEPLOYED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.WHITELIST_REQUESTED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.WHITELIST_HASH_RETRIEVED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.WHITELIST_HASH_SIGNED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED)
                .and()
                .withExternal()
                .source(DeploymentState.WHITELIST_APPROVED)
                .target(DeploymentState.ERROR)
                .event(DeploymentEvent.ERROR_OCCURRED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<DeploymentState, DeploymentEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Bean
    public StateMachineListener<DeploymentState, DeploymentEvent> listener() {
        return new StateMachineListenerAdapter<DeploymentState, DeploymentEvent>() {
            @Override
            public void stateChanged(State<DeploymentState, DeploymentEvent> from, State<DeploymentState, DeploymentEvent> to) {
                if (from != null) {
                    log.info("State changed from {} to {}", from.getId(), to.getId());
                } else {
                    log.info("State initialized to {}", to.getId());
                }
            }
        };
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
