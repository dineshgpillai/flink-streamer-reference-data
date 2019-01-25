Feature: constructALegalDocumentandPushToHz

  Scenario: Connect to Input source with legal CSA details and store in Hz
    Given the following file in this location contains the CSA data "legal-documents/legal-ex09-scsa-2014-new-york.xml"
    When the streamer reads this file from source
    Then stores the legal documents to Hz which should contain 12 legal documents 3 Parties