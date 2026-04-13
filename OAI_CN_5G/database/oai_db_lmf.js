db = db.getSiblingDB('oai_db_mongo');

/******************************************
*
* AccessAndMobilitySubscriptionData
*
*******************************************/
// Schema for collection 'AccessAndMobilitySubscriptionData'
db.createCollection('AccessAndMobilitySubscriptionData', {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "servingPlmnid"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "must be a string and is required"
                },
                servingPlmnid: {
                    bsonType: "string",
                    description: "must be a string and is required"
                },
                supportedFeatures: {
                    bsonType: "string",
                    description: "must be a string"
                },
                gpsis: {
                    bsonType: "object",
                    description: "must be an object"
                },
                internalGroupIds: {
                    bsonType: "object",
                    description: "must be an object"
                },
                sharedVnGroupDataIds: {
                    bsonType: "object",
                    description: "must be an object"
                },
                subscribedUeAmbr: {
                    bsonType: "object",
                    description: "must be an object"
                },
                nssai: {
                    bsonType: "object",
                    description: "must be an object"
                },
                ratRestrictions: {
                    bsonType: "object",
                    description: "must be an object"
                },
                forbiddenAreas: {
                    bsonType: "object",
                    description: "must be an object"
                },
                serviceAreaRestriction: {
                    bsonType: "object",
                    description: "must be an object"
                },
                coreNetworkTypeRestrictions: {
                    bsonType: "object",
                    description: "must be an object"
                },
                rfspIndex: {
                    bsonType: "int",
                    description: "must be an integer"
                },
                subsRegTimer: {
                    bsonType: "int",
                    description: "must be an integer"
                },
                ueUsageType: {
                    bsonType: "int",
                    description: "must be an integer"
                },
                mpsPriority: {
                    bsonType: "bool",
                    description: "must be a boolean"
                },
                mcsPriority: {
                    bsonType: "bool",
                    description: "must be a boolean"
                },
                activeTime: {
                    bsonType: "int",
                    description: "must be an integer"
                },
                sorInfo: {
                    bsonType: "object",
                    description: "must be an object"
                },
                sorInfoExpectInd: {
                    bsonType: "bool",
                    description: "must be a boolean"
                },
                sorafRetrieval: {
                    bsonType: "bool",
                    description: "must be a boolean"
                },
                sorUpdateIndicatorList: {
                    bsonType: "object",
                    description: "must be an object"
                },
                upuInfo: {
                    bsonType: "object",
                    description: "must be an object"
                },
                micoAllowed: {
                    bsonType: "bool",
                    description: "must be a boolean"
                },
                sharedAmDataIds: {
                    bsonType: "object",
                    description: "must be an object"
                },
                odbPacketServices: {
                    bsonType: "object",
                    description: "must be an object"
                },
                serviceGapTime: {
                    bsonType: "int",
                    description: "must be an integer"
                },
                mdtUserConsent: {
                    bsonType: "object",
                    description: "must be an object"
                },
                mdtConfiguration: {
                    bsonType: "object",
                    description: "Configuration for MDT"
                },
                traceData: {
                    bsonType: "object",
                    description: "Trace data"
                },
                cagData: {
                    bsonType: "object",
                    description: "CAG data"
                },
                stnSr: {
                    bsonType: "string",
                    description: "STN-SR"
                },
                cMsisdn: {
                    bsonType: "string",
                    description: "Combined MSISDN"
                },
                nbIoTUePriority: {
                    bsonType: "int",
                    description: "NB-IoT UE priority"
                },
                nssaiInclusionAllowed: {
                    bsonType: "bool",
                    description: "Flag indicating if NSSAI inclusion is allowed"
                },
                rgWirelineCharacteristics: {
                    bsonType: "string",
                    description: "Characteristics of RG wireline"
                },
                ecRestrictionDataWb: {
                    bsonType: "object",
                    description: "Restriction data for EC on WB"
                },
                ecRestrictionDataNb: {
                    bsonType: "bool",
                    description: "Flag indicating if EC restriction is allowed on NB"
                },
                expectedUeBehaviourList: {
                    bsonType: "object",
                    description: "List of expected UE behaviours"
                },
                primaryRatRestrictions: {
                    bsonType: "object",
                    description: "Primary RAT restrictions"
                },
                secondaryRatRestrictions: {
                    bsonType: "object",
                    description: "Secondary RAT restrictions"
                },
                edrxParametersList: {
                    bsonType: "object",
                    description: "List of eDRX parameters"
                },
                ptwParametersList: {
                    bsonType: "object",
                    description: "List of PTW parameters"
                },
                iabOperationAllowed: {
                    bsonType: "bool",
                    description: "Flag indicating if IAB operation is allowed"
                },
                wirelineForbiddenAreas: {
                    bsonType: "object",
                    description: "Forbidden wireline areas"
                },
                wirelineServiceAreaRestriction: {
                    bsonType: "object",
                    description: "Restriction for wireline service areas"
                }
            }
        }
    }
});

// Indexes for collection 'AccessAndMobilitySubscriptionData'
db.AccessAndMobilitySubscriptionData.createIndex({ ueid: 1, servingPlmnid: 1 });

// Inserts for collection 'AccessAndMobilitySubscriptionData'
db.AccessAndMobilitySubscriptionData.insertMany([
    {
        ueid: '001010000000001',
        servingPlmnid: '00101',
        nssai: {
            defaultSingleNssais: [{
                'sst': 1,
                'sd': '000001'
            }]
        }
    },
    {
        ueid: '001010000000002',
        servingPlmnid: '00101',
        nssai: {
            defaultSingleNssais: [{
                'sst': 1,
                'sd': '000001'
            }]
        }
    },
    {
        ueid: '001010000000003',
        servingPlmnid: '00101',
        nssai: {
            defaultSingleNssais: [{
                'sst': 1,
                'sd': '000001'
            }]
        }
    }
]);


/******************************************
*
* Amf3GppAccessRegistration
*
*******************************************/
// Schema for collection 'Amf3GppAccessRegistration'
db.createCollection("Amf3GppAccessRegistration", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "amfInstanceId", "deregCallbackUri", "guami", "ratType"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "Unique identifier for the UE"
                },
                amfInstanceId: {
                    bsonType: "string",
                    description: "AMF instance identifier"
                },
                supportedFeatures: {
                    bsonType: "string",
                    description: "Supported features"
                },
                purgeFlag: {
                    bsonType: "bool",
                    description: "Purge flag"
                },
                pei: {
                    bsonType: "string",
                    description: "Pei"
                },
                imsVoPs: {
                    bsonType: "object",
                    description: "IMS VoPs"
                },
                deregCallbackUri: {
                    bsonType: "string",
                    description: "Deregistration callback URI"
                },
                amfServiceNameDereg: {
                    bsonType: "object",
                    description: "AMF service name for deregistration"
                },
                pcscfRestorationCallbackUri: {
                    bsonType: "string",
                    description: "PCSCF restoration callback URI"
                },
                amfServiceNamePcscfRest: {
                    bsonType: "object",
                    description: "AMF service name for PCSCF restoration"
                },
                initialRegistrationInd: {
                    bsonType: "bool",
                    description: "Initial registration indicator"
                },
                guami: {
                    bsonType: "object",
                    description: "Globally unique AMF identifier"
                },
                backupAmfInfo: {
                    bsonType: "object",
                    description: "Backup AMF information"
                },
                drFlag: {
                    bsonType: "bool",
                    description: "DR flag"
                },
                ratType: {
                    bsonType: "string",
                    description: "Radio access technology type"
                },
                urrpIndicator: {
                    bsonType: "bool",
                    description: "URRP indicator"
                },
                amfEeSubscriptionId: {
                    bsonType: "string",
                    description: "AMF EE subscription ID"
                },
                epsInterworkingInfo: {
                    bsonType: "object",
                    description: "EPS interworking information"
                },
                ueSrvccCapability: {
                    bsonType: "bool",
                    description: "UE SRVCC capability"
                },
                registrationTime: {
                    bsonType: "string",
                    description: "Registration time"
                },
                vgmlcAddress: {
                    bsonType: "object",
                    description: "VGMLC address"
                },
                contextInfo: {
                    bsonType: "object",
                    description: "Context information"
                },
                noEeSubscriptionInd: {
                    bsonType: "bool",
                    description: "No EE subscription indicator"
                }
            }
        }
    }
});

// Index for collection 'Amf3GppAccessRegistration'
db.Amf3GppAccessRegistration.createIndex({ ueid: 1 });

// Inserts for collection 'Amf3GppAccessRegistration'


/******************************************
*
* AuthenticationStatus
*
*******************************************/
// Schema for collection 'AuthenticationStatus'
db.createCollection("AuthenticationStatus", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "nfInstanceId", "success", "timeStamp", "authType", "servingNetworkName"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "Unique identifier for the UE"
                },
                nfInstanceId: {
                    bsonType: "string",
                    description: "NF instance identifier"
                },
                success: {
                    bsonType: "bool",
                    description: "Authentication success status"
                },
                timeStamp: {
                    bsonType: "string",
                    description: "Time stamp"
                },
                authType: {
                    bsonType: "string",
                    description: "Authentication type"
                },
                servingNetworkName: {
                    bsonType: "string",
                    description: "Serving network name"
                },
                authRemovalInd: {
                    bsonType: "bool",
                    description: "Authentication removal indicator"
                }
            }
        }
    }
});

// Index for collection 'AuthenticationStatus'
db.AuthenticationStatus.createIndex({ ueid: 1 });

// Inserts for collection 'AuthenticationStatus'


/******************************************
*
* AuthenticationSubscription
*
********************************************/
// Schema for collection 'AuthenticationSubscription'
db.createCollection("AuthenticationSubscription", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "authenticationMethod"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "UE ID"
                },
                authenticationMethod: {
                    bsonType: "string",
                    description: "Authentication Method"
                },
                encPermanentKey: {
                    bsonType: "string",
                    description: "Encrypted Permanent Key"
                },
                protectionParameterId: {
                    bsonType: "string",
                    description: "Protection Parameter ID"
                },
                sequenceNumber: {
                    bsonType: "object",
                    description: "Sequence Number",
                    additionalProperties: true
                },
                authenticationManagementField: {
                    bsonType: "string",
                    description: "Authentication Management Field"
                },
                algorithmId: {
                    bsonType: "string",
                    description: "Algorithm ID"
                },
                encOpcKey: {
                    bsonType: "string",
                    description: "Encrypted OPC Key"
                },
                encTopcKey: {
                    bsonType: "string",
                    description: "Encrypted TOPC Key"
                },
                vectorGenerationInHss: {
                    bsonType: "bool",
                    description: "Vector Generation in HSS"
                },
                n5gcAuthMethod: {
                    bsonType: "string",
                    description: "N5GC Authentication Method"
                },
                rgAuthenticationInd: {
                    bsonType: "bool",
                    description: "RG Authentication Indicator"
                },
                supi: {
                    bsonType: "string",
                    description: "SUPI"
                }
            }
        }
    }
});

// Index for collection 'AuthenticationSubscription'
db.AuthenticationSubscription.createIndex({ ueid: 1 });

// Inserts for collection 'AuthenticationSubscription'
db.AuthenticationSubscription.insertMany([
    {"ueid": "001010000000001",
    "authenticationMethod": "5G_AKA",
    "encPermanentKey": "fec86ba6eb707ed08905757b1bb44b8f",
    "protectionParameterId": "fec86ba6eb707ed08905757b1bb44b8f",
    "sequenceNumber":
        {"sqn": "000000000020",
            "sqnScheme": "NON_TIME_BASED",
            "lastIndexes": {"ausf": 0} },
    "authenticationManagementField": "8000",
    "algorithmId": "milenage",
    "encOpcKey": "C42449363BBAD02B66D16BC975D77CC1",
    "supi": "001010000000001"  },
    {"ueid": "001010000000002",
        "authenticationMethod": "5G_AKA",
        "encPermanentKey": "fec86ba6eb707ed08905757b1bb44b8f",
        "protectionParameterId": "fec86ba6eb707ed08905757b1bb44b8f",
        "sequenceNumber":
            {"sqn": "000000000020",
                "sqnScheme": "NON_TIME_BASED",
                "lastIndexes": {"ausf": 0} },
        "authenticationManagementField": "8000",
        "algorithmId": "milenage",
        "encOpcKey": "C42449363BBAD02B66D16BC975D77CC1",
        "supi": "001010000000002"  },
    {"ueid": "001010000000003",
        "authenticationMethod": "5G_AKA",
        "encPermanentKey": "fec86ba6eb707ed08905757b1bb44b8f",
        "protectionParameterId": "fec86ba6eb707ed08905757b1bb44b8f",
        "sequenceNumber":
            {"sqn": "000000000020",
                "sqnScheme": "NON_TIME_BASED",
                "lastIndexes": {"ausf": 0} },
        "authenticationManagementField": "8000",
        "algorithmId": "milenage",
        "encOpcKey": "C42449363BBAD02B66D16BC975D77CC1",
        "supi": "001010000000003"  }
]);


/******************************************
*
* SdmSubscriptions
*
*******************************************/
// Schema for collection 'SdmSubscriptions'
db.createCollection("SdmSubscriptions", {
        validator: {
            $jsonSchema: {
                bsonType: "object",
                required: ["ueid", "subsId", "nfInstanceId", "callbackReference", "monitoredResourceUris"],
                properties: {
                    ueid: { bsonType: "string", description: "Network function instance identifier" },
                    implicitUnsubscribe: { bsonType: "bool", description: "Indicates whether the subscription is implicitly unsubscribed" },
                    expires: { bsonType: "string", description: "Expiration date/time of subscription" },
                    callbackReference: { bsonType: "string", description: "Callback reference of subscription" },
                    amfServiceName: { bsonType: "object", description: "AMF service name of subscription" },
                    monitoredResourceUris: { bsonType: "array", description: "Array of monitored resource URIs" },
                    singleNssai: { bsonType: "object", description: "Single Network Slice Selection Assistance Information" },
                    dnn: { bsonType: "string", description: "Data Network Name of subscription" },
                    subscriptionId: { bsonType: "string", description: "Subscription ID of subscription" },
                    plmnId: { bsonType: "object", description: "Public Land Mobile Network ID of subscription" },
                    immediateReport: { bsonType: "bool", description: "Indicates whether immediate reports are enabled for the subscription" },
                    report: { bsonType: "object", description: "Report configuration of subscription" },
                    supportedFeatures: { bsonType: "string", description: "Supported features of subscription" },
                    contextInfo: { bsonType: "object", description: "Context information of subscription" }
                }
            }
        }
    }
);

// Index for collection 'SdmSubscriptions'
db.SdmSubscriptions.createIndex({ subsId: 1, ueid: 1 });

db.SdmSubscriptions.createIndex({ subsId: 1 }, { unique: true });
db.SdmSubscriptions.find().forEach(function(doc) {
    const nextValue = db.SdmSubscriptions.find().count() + 1;
    db.SdmSubscriptions.update({ _id: doc._id }, { $set: { subsId: nextValue } });
});

// Inserts for collection 'SdmSubscriptions'


/******************************************
 *
 * SessionManagementSubscriptionData
 *
 *******************************************/
// Schema for collection 'SessionManagementSubscriptionData'
db.createCollection('SessionManagementSubscriptionData', {
        validator: {
            $jsonSchema: {
                bsonType: "object",
                required: [ "ueid", "servingPlmnid", "singleNssai", "dnnConfigurations" ],
                properties: {
                    ueid: {
                        bsonType: "string",
                        description: "Unique UE ID"
                    },
                    servingPlmnid: {
                        bsonType: "string",
                        description: "Serving PLMN ID"
                    },
                    singleNssai: {
                        bsonType: "object",
                        description: "Single Network Slice Selection Assistance Information"
                    },
                    dnnConfigurations: {
                        bsonType: "object",
                        description: "DNN (Data Network Name) Configurations"
                    },
                    internalGroupIds: {
                        bsonType: "object",
                        description: "Internal Group Identifiers"
                    },
                    sharedVnGroupDataIds: {
                        bsonType: "object",
                        description: "Shared VN (Virtual Network) Group Data Identifiers"
                    },
                    sharedDnnConfigurationsId: {
                        bsonType: "string",
                        description: "Shared DNN Configurations Identifier"
                    },
                    odbPacketServices: {
                        bsonType: "object",
                        description: "ODB (Overload Data Bit) Packet Services"
                    },
                    traceData: {
                        bsonType: "object",
                        description: "Trace Data"
                    },
                    sharedTraceDataId: {
                        bsonType: "string",
                        description: "Shared Trace Data Identifier"
                    },
                    expectedUeBehavioursList: {
                        bsonType: "object",
                        description: "Expected UE Behaviours List"
                    },
                    suggestedPacketNumDlList: {
                        bsonType: "object",
                        description: "Suggested Packet Number Downlink List"
                    },
                    threegppChargingCharacteristics: {
                        bsonType: "string",
                        description: "3GPP Charging Characteristics"
                    }
                }
            }
        }
    });

// Index for collection 'SessionManagementSubscriptionData'
db.SessionManagementSubscriptionData.createIndex({ ueid: 1, servingPlmnid: 1 });

// Inserts for collection 'SessionManagementSubscriptionData'
db.SessionManagementSubscriptionData.insertMany([
    {
        ueid: "001010000000031",
        servingPlmnid: "00101",
        singleNssai: {
            sst: 1,
            sd: "000001"
        },
        dnnConfigurations: {
            default: {
                pduSessionTypes: {
                    defaultSessionType: "IPV4"
                },
                sscModes: {
                    defaultSscMode: "SSC_MODE_1"
                },
                "5gQosProfile": {
                    "5qi": 6,
                    arp: {
                        priorityLevel: 1,
                        preemptCap: "NOT_PREEMPT",
                        preemptVuln: "NOT_PREEMPTABLE"
                    },
                    priorityLevel: 1
                },
                sessionAmbr: {
                    uplink: "100Mbps",
                    downlink: "100Mbps"
                },
                staticIpAddress: [
                    {
                        ipv4Addr: "12.1.1.130"
                    }
                ]
            }
        }
    },
    {
        ueid: "001010000000032",
        servingPlmnid: "00101",
        singleNssai: {
            sst: 1,
            sd: "000001"
        },
        dnnConfigurations: {
            default: {
                pduSessionTypes: {
                    defaultSessionType: "IPV4"
                },
                sscModes: {
                    defaultSscMode: "SSC_MODE_1"
                },
                "5gQosProfile": {
                    "5qi": 6,
                    arp: {
                        priorityLevel: 1,
                        preemptCap: "NOT_PREEMPT",
                        preemptVuln: "NOT_PREEMPTABLE"
                    },
                    priorityLevel: 1
                },
                sessionAmbr: {
                    uplink: "100Mbps",
                    downlink: "100Mbps"
                }
            }
        }
    },
    {
        ueid: "001010000000010",
        servingPlmnid: "00101",
        singleNssai: {
            sst: 1,
            sd: "000001"
        },
        dnnConfigurations: {
            default: {
                pduSessionTypes: {
                    defaultSessionType: "IPV4"
                },
                sscModes: {
                    defaultSscMode: "SSC_MODE_1"
                },
                "5gQosProfile": {
                    "5qi": 9,
                    arp: {
                        priorityLevel: 1,
                        preemptCap: "NOT_PREEMPT",
                        preemptVuln: "NOT_PREEMPTABLE"
                    },
                    priorityLevel: 1
                },
                sessionAmbr: {
                    uplink: "200Mbps",
                    downlink: "400Mbps"
                },
                staticIpAddress: [
                    {
                        ipv4Addr: "12.1.1.154"
                    }
                ]
            }
        }
    }
]);


/******************************************
 *
 * SmfRegistrations
 *
 *******************************************/
// Schema for collection 'SmfRegistrations'
db.createCollection("SmfRegistrations", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "subpduSessionId", "smfInstanceId", "singleNssai", "pduSessionId", "plmnId"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "UE ID"
                },
                subpduSessionId: {
                    bsonType: "int",
                    description: "Sub PDU session ID"
                },
                smfInstanceId: {
                    bsonType: "string",
                    description: "SMF instance ID"
                },
                smfSetId: {
                    bsonType: "string",
                    description: "SMF set ID"
                },
                supportedFeatures: {
                    bsonType: "string",
                    description: "Supported features"
                },
                pduSessionId: {
                    bsonType: "int",
                    description: "PDU session ID"
                },
                singleNssai: {
                    bsonType: "object",
                    description: "Single Network Slice Selection Assistance Information"
                },
                dnn: {
                    bsonType: "string",
                    description: "Data Network Name"
                },
                emergencyServices: {
                    bsonType: "bool",
                    description: "Indicates if emergency services are supported"
                },
                pcscfRestorationCallbackUri: {
                    bsonType: "string",
                    description: "PCF restoration callback URI"
                },
                plmnId: {
                    bsonType: "object",
                    description: "Public Land Mobile Network Identifier"
                },
                pgwFqdn: {
                    bsonType: "string",
                    description: "PDN Gateway Fully Qualified Domain Name"
                },
                epdgInd: {
                    bsonType: "bool",
                    description: "Indicates if ePDG is supported"
                },
                deregCallbackUri: {
                    bsonType: "string",
                    description: "Deregistration callback URI"
                },
                registrationReason: {
                    bsonType: "object",
                    description: "Registration reason"
                },
                registrationTime: {
                    bsonType: "string",
                    description: "Registration time"
                },
                contextInfo: {
                    bsonType: "object",
                    description: "Context information"
                }
            }
        }
    }
});

// Index for collection 'SmfRegistrations'
db.SmfRegistrations.createIndex({ ueid: 1, subpduSessionId: 1 });

// Inserts for collection 'SmfRegistrations'


/******************************************
 *
 * SmfSelectionSubscriptionData
 *
 *******************************************/
// Schema for collection 'SmfSelectionSubscriptionData'
db.createCollection("SmfSelectionSubscriptionData", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["ueid", "servingPlmnid", "supportedFeatures"],
            properties: {
                ueid: {
                    bsonType: "string",
                    description: "UE identifier"
                },
                servingPlmnid: {
                    bsonType: "string",
                    description: "PLMN identifier"
                },
                supportedFeatures: {
                    bsonType: "string",
                    description: "Supported features"
                },
                subscribedSnssaiInfos: {
                    bsonType: "object",
                    description: "Subscribed S-NSSAI information"
                },
                sharedSnssaiInfosId: {
                    bsonType: "string",
                    description: "ID of shared S-NSSAI information"
                }
            }
        }
    }
});

// Index for collection 'SmfSelectionSubscriptionData'
db.SmfSelectionSubscriptionData.createIndex({ ueid: 1, servingPlmnid: 1 });

// Inserts for collection 'SmfSelectionSubscriptionData'
