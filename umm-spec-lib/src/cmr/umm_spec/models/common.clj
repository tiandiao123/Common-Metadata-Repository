;; WARNING: This file was generated from umm-cmn-json-schema.json. Do not manually modify.
(ns cmr.umm-spec.models.common
   "Defines UMM Common clojure records."
 (:require [cmr.common.dev.record-pretty-printer :as record-pretty-printer]))

;; This element describes the relevant platforms used to acquire the data. Platform types are
;; controlled and include Spacecraft, Aircraft, Vessel, Buoy, Platform, Station, Network, Human,
;; etc.
(defrecord PlatformType
  [
   ;; The most relevant platform type.
   Type

   ShortName

   LongName

   ;; The characteristics of platform specific attributes. The characteristic names must be unique
   ;; on this platform; however the names do not have to be unique across platforms.
   Characteristics

   Instruments
  ])
(record-pretty-printer/enable-record-pretty-printing PlatformType)

;; This entity contains attributes describing the scientific endeavor(s) to which the collection is
;; associated. Scientific endeavors include campaigns, projects, interdisciplinary science
;; investigations, missions, field experiments, etc.
(defrecord ProjectType
  [
   ;; The unique identifier by which a project or campaign/experiment is known. The campain/project
   ;; is the scientific endeavor associated with the acquisition of the collection. Collections may
   ;; be associated with multiple campaigns.
   ShortName

   ;; The expanded name of the campaign/experiment (e.g. Global climate observing system).
   LongName

   ;; The name of the campaign/experiment (e.g. Global climate observing system).
   Campaigns

   ;; The starting date of the campaign.
   StartDate

   ;; The ending data of the campaign.
   EndDate
  ])
(record-pretty-printer/enable-record-pretty-printing ProjectType)

;; This entity registers the device used to measure or record data, including direct human
;; observation. In cases where instruments have a single sensor or the instrument and sensor are
;; used synonymously (e.g. AVHRR) the both Instrument and Sensor should be recorded. The Sensor
;; information is represented by some other entities.
(defrecord InstrumentType
  [
   ShortName

   LongName

   ;; The characteristics of this instrument expressed as custom attributes. The characteristic
   ;; names must be unique on this instrument; however the names do not have to be unique across
   ;; instruments.
   Characteristics

   ;; The expanded name of the primary sensory instrument. (e.g. Advanced Spaceborne Thermal
   ;; Emission and Reflective Radiometer, Clouds and the Earth's Radiant Energy System, Human
   ;; Observation).
   Technique

   ;; Number of sensors used on the instrument when acquire the granule data.
   NumberOfSensors

   Sensors

   ;; The operation mode applied on the instrument when acquire the granule data.
   OperationalModes
  ])
(record-pretty-printer/enable-record-pretty-printing InstrumentType)

;; This entity holds collection horizontal spatial coverage data.
(defrecord HorizontalSpatialDomainType
  [
   ;; The appropriate numeric or alpha code used to identify the various zones in this grid
   ;; coordinate system.
   ZoneIdentifier

   Geometry
  ])
(record-pretty-printer/enable-record-pretty-printing HorizontalSpatialDomainType)

;; Contains the excluded boundaries from the GPolygon.
(defrecord ExclusiveZoneType
  [
   Boundaries
  ])
(record-pretty-printer/enable-record-pretty-printing ExclusiveZoneType)

;; This element describes the relevant platforms used to acquire the data related to the service.
;; Platform types are controlled and include Spacecraft, Aircraft, Vessel, Buoy, Platform, Station,
;; Network, Human, etc. This platform includes zero or more instruments
(defrecord TilingIdentificationSystemType
  [
   TilingIdentificationSystemName

   Coordinate1

   Coordinate2
  ])
(record-pretty-printer/enable-record-pretty-printing TilingIdentificationSystemType)

;; Specifies the geographic and vertical (altitude, depth) coverage of the data.
(defrecord SpatialExtentType
  [
   ;; This attribute denotes whether the collection's spatial coverage requires horizontal,
   ;; vertical, or both in the spatial domain and coordinate system definitions.
   SpatialCoverageType

   HorizontalSpatialDomain

   VerticalSpatialDomains

   OrbitParameters

   GranuleSpatialRepresentation
  ])
(record-pretty-printer/enable-record-pretty-printing SpatialExtentType)

;; Describes the Records the data and the changes that happened on that date for the metadata or
;; data (the metadata or data is described by the scope element.)
(defrecord LineageType
  [
   ;; Describes the data held as either describing the metadata or data.
   Scope

   ;; This encaptulates all of the changes for a specific date.
   Dates
  ])
(record-pretty-printer/enable-record-pretty-printing LineageType)

;; This element describes media options, size, data format, and fees involved in distributing or
;; accessing the data.
(defrecord DistributionType
  [
   ;; The distribution media of the data or service.
   DistributionMedia

   ;; The size of the distribution package of the data or service.
   DistributionSize

   ;; The distribution format of the data.
   DistributionFormat

   ;; The fee for ordering the data or service.
   Fees
  ])
(record-pretty-printer/enable-record-pretty-printing DistributionType)

(defrecord PartyType
  [
   OrganizationName

   Person

   ;; Time period when individuals can speak to the organization or person.
   ServiceHours

   ;; Supplemental instructions on how or when to contact the organization or person.
   ContactInstructions

   ;; Contacts including phone, fax, email, url, etc.
   Contacts

   ;; The address of the organization or person
   Addresses

   ;; The URL of the organization or individual
   RelatedUrls
  ])
(record-pretty-printer/enable-record-pretty-printing PartyType)

;; This element permits the user to properly cite the provider and specifies how the data should be
;; cited in professional scientific literature. This element provides a citation for the item
;; itself, and is not designed for listing bibliographic references of scientific research articles
;; arising from search results. A list of references related to the research results should be in
;; the Publication Reference element. A DOI that specifically identifies the service is listed here.
(defrecord ResourceCitationType
  [
   ;; The name of the data series, or aggregate data of which the data is a part.
   SeriesName

   ;; The name of the organization(s) or individual(s) with primary intellectual responsibility for
   ;; the data's development.
   Creator

   ;; The name of the city (and state or province and country if needed) where the data set was made
   ;; available for release.
   ReleasePlace

   ;; The title of the data; this may be the same as Entry Title.
   Title

   ;; The Digital Object Identifier (DOI) of a data set.
   DOI

   ;; The name of the individual or organization that made the data available for release.
   Publisher

   ;; The date when the data was made available for release.
   ReleaseDate

   ;; The URL of the online resource containing the data set.
   RelatedUrl

   ;; The volume or issue number of the publication (if applicable).
   IssueIdentification

   ;; The individual(s) responsible for changing the data.
   Editor

   ;; The mode in which the data are represented, e.g. atlas, image, profile, text, etc.
   DataPresentationForm

   ;; The version of the citation.
   Version

   ;; Additional free-text citation information.
   OtherCitationDetails
  ])
(record-pretty-printer/enable-record-pretty-printing ResourceCitationType)

;; This element describes the digital object identifier.
(defrecord DoiType
  [
   ;; The authority who created the DOI.
   Authority

   ;; The Digitial Object Identifier.
   DOI
  ])
(record-pretty-printer/enable-record-pretty-printing DoiType)

;; Stores the start and end date/time of a collection.
(defrecord RangeDateTimeType
  [
   ;; The time when the temporal coverage period being described began.
   BeginningDateTime

   ;; The time of the temporal coverage period being described ended.
   EndingDateTime
  ])
(record-pretty-printer/enable-record-pretty-printing RangeDateTimeType)

(defrecord BoundingRectangleType
  [
   CenterPoint

   WestBoundingCoordinate

   NorthBoundingCoordinate

   EastBoundingCoordinate

   SouthBoundingCoordinate
  ])
(record-pretty-printer/enable-record-pretty-printing BoundingRectangleType)

(defrecord LineType
  [
   Points

   CenterPoint
  ])
(record-pretty-printer/enable-record-pretty-printing LineType)

;; This element enables specification of Earth science keywords.
(defrecord ScienceKeywordType
  [
   Category

   Topic

   Term

   VariableLevel1

   VariableLevel2

   VariableLevel3

   DetailedVariable
  ])
(record-pretty-printer/enable-record-pretty-printing ScienceKeywordType)

;; This entity stores the data’s distinctive attributes (i.e. attributes used to describe the unique
;; characteristics of the service which extend beyond those defined in this mapping).
(defrecord AdditionalAttributeType
  [
   ;; Group identifies a namespace for the value.
   Group

   ;; The standard unit of measurement for a not-core attribute. AVHRR: unit of geophysical
   ;; parameter-units of geophysical parameter.
   ParameterUnitsOfMeasure

   ;; An estimate of the accuracy of the assignment of attribute value. AVHRR: Measurement error or
   ;; precision-measurement error or precision of a data product parameter. This can be specified in
   ;; percent or the unit with which the parameter is measured.
   ParameterValueAccuracy

   ;; This attribute will be used to identify the smallest unit increment to which the parameter
   ;; value is measured.
   MeasurementResolution

   ;; This attribute provides the minimum value of parameter over whole collection.
   ParameterRangeBegin

   ;; This defines the method used for determining the parameter value accuracy that is given for
   ;; this non core attribute.
   ValueAccuracyExplanation

   ;; This attribute contains the value ofthe product specific attribute (additional attribute) for
   ;; all granules across a given collection
   Value

   Name

   ;; This attribute provides a description for the additional_attribute_name.
   Description

   ;; The date this attribute was updated.
   UpdateDate

   ;; This attribute provides the maximum value of parameter over whole collection.
   ParameterRangeEnd

   ;; Data type of parameter value
   DataType
  ])
(record-pretty-printer/enable-record-pretty-printing AdditionalAttributeType)

;; Field allows the author to provide information about any constraints for accessing the data set.
;; This includes any special restrictions, legal prerequisites, limitations and/or warnings on
;; obtaining the data set. Some words that may be used in this field include: Public, In-house,
;; Limited, Additional detailed instructions on how to access the data can be entered in this field.
(defrecord AccessConstraintsType
  [
   ;; Description of the constraint
   Description

   ;; Value of the constraint
   Value
  ])
(record-pretty-printer/enable-record-pretty-printing AccessConstraintsType)

(defrecord VerticalSpatialDomainType
  [
   ;; This attribute describes the type of the area of vertical space covered by the locality.
   Type

   ;; This attribute describes the extent of the area of vertical space covered by the granule. Must
   ;; be accompanied by an Altitude Encoding Method description. The datatype for this attribute is
   ;; the value of the attribute VerticalSpatialDomainType. The unit for this attribute is the value
   ;; of either DepthDistanceUnits or AltitudeDistanceUnits.
   Value
  ])
(record-pretty-printer/enable-record-pretty-printing VerticalSpatialDomainType)

;; consists of a contact. A contact, can be phone, fax, email, url, etc.
(defrecord ContactType
  [
   ;; This states the type of contact - phone, fax, email, url, etc.
   Type

   ;; This is the contact phone number, email address, url, etc.
   Value
  ])
(record-pretty-printer/enable-record-pretty-printing ContactType)

(defrecord GeometryType
  [
   CoordinateSystem

   Points

   BoundingRectangles

   GPolygons

   Lines
  ])
(record-pretty-printer/enable-record-pretty-printing GeometryType)

;; This entity holds the referential information for collection source/sensor configuration
;; including sensor parameters setting such as technique etc.
(defrecord SensorType
  [
   ShortName

   LongName

   ;; The characteristics of this sensor expressed as custom attributes. The characteristic names
   ;; must be unique on this sensor; however the names do not have to be unique across sensors.
   Characteristics

   ;; Technique applied for this sensor in the configuration.
   Technique
  ])
(record-pretty-printer/enable-record-pretty-printing SensorType)

;; The longitude and latitude value of a spatially referenced point in degrees.
(defrecord PointType
  [
   Longitude

   Latitude
  ])
(record-pretty-printer/enable-record-pretty-printing PointType)

;; This element describes key bibliographic citations pertaining to the data.
(defrecord PublicationReferenceType
  [
   ;; The date of the publication.
   PublicationDate

   ;; Additional free-text reference information.
   OtherReferenceDetails

   ;; The name of the series.
   Series

   ;; The title of the publication.
   Title

   ;; The Digital Object Identifier (DOI) of the publication.
   DOI

   ;; The publication pages that are relevant.
   Pages

   ;; The edition of the publication.
   Edition

   ;; The report number of the publication.
   ReportNumber

   ;; The publication volume number.
   Volume

   ;; The publisher of the publication.
   Publisher

   ;; The URL of the online resource containing the data set.
   RelatedUrl

   ;; The ISBN of the publication.
   ISBN

   ;; The author of the publication reference.
   Author

   ;; The issue of the publication.
   Issue

   ;; The pubication place of the publication.
   PublicationPlace
  ])
(record-pretty-printer/enable-record-pretty-printing PublicationReferenceType)

;; This element is used to identify other services, collections, visualizations, granules, and other
;; metadata types and resources that are associated with or dependent on the data described by the
;; metadata. This element is also used to identify a parent metadata record if it exists. This usage
;; should be reserved for instances where a group of metadata records are subsets that can be better
;; represented by one parent metadata record, which describes the entire set. In some instances, a
;; child may point to more than one parent. The EntryId is the same as the element described
;; elsewhere in this document where it contains and ID, and Version.
(defrecord MetadataAssociationType
  [
   ;; Type is used to indicate the basis (justification) for relating one resource to another
   Type

   ;; The description of the association.
   Description

   ;; ID of the metadata record that is associated with this record.
   EntryId

   ;; The version of the metadata record that is associated with this record.
   Version
  ])
(record-pretty-printer/enable-record-pretty-printing MetadataAssociationType)

(defrecord FileSizeType
  [
   ;; The size of the download or site.
   Size

   ;; Unit of the size type. If no size is specified MegaBytes is assumed. This should be changed to
   ;; an enumeration type
   Unit
  ])
(record-pretty-printer/enable-record-pretty-printing FileSizeType)

;; Defines the minimum and maximum value for one dimension of a two dimensional coordinate.
(defrecord TilingCoordinateType
  [
   MinimumValue

   MaximumValue
  ])
(record-pretty-printer/enable-record-pretty-printing TilingCoordinateType)

(defrecord GPolygonType
  [
   CenterPoint

   Boundary

   ExclusiveZone
  ])
(record-pretty-printer/enable-record-pretty-printing GPolygonType)

;; A boundary is set of points connected by straight lines representing a polygon on the earth. It
;; takes a minimum of three points to make a boundary. Points must be specified in counter-clockwise
;; order and closed (the first and last vertices are the same).
(defrecord BoundaryType
  [
   Points
  ])
(record-pretty-printer/enable-record-pretty-printing BoundaryType)

;; Specifies the date and its type.
(defrecord DateType
  [
   ;; This is the date a creation, update, or deletion occurred.
   Date

   ;; This is the type of date: create, update, review, delete.
   Type
  ])
(record-pretty-printer/enable-record-pretty-printing DateType)

;; This entity is used to define characteristics.
(defrecord CharacteristicType
  [
   ;; The name of the characteristic attribute.
   Name

   ;; Description of the Characteristic attribute.
   Description

   ;; The value of the Characteristic attribute.
   Value

   ;; Units associated with the Characteristic attribute value.
   Unit

   ;; The datatype of the Characteristic/attribute.
   DataType
  ])
(record-pretty-printer/enable-record-pretty-printing CharacteristicType)

;; defines the point of contact for more information about the data set or the metadata. The contact
;; personnel are defined by the Role, which include: Investigator: The person who headed the
;; investigation or experiment that resulted in the acquisition of the data described (i.e.,
;; Principal Investigator, Experiment Team Leader) Technical Contact: The person who is
;; knowledgeable about the technical content of the data (quality, processing methods, units,
;; available software for further processing) Metadata Author: The person who is responsible for the
;; content of the Metadata. If the responsibility shifts from the original author to another person,
;; the Metadata Author field should be updated to the new responsible person.
(defrecord PersonType
  [
   ;; First name of the individual.
   FirstName

   ;; Middle name of the individual.
   MiddleName

   ;; Last name of the individual.
   LastName

   ;; Uuid of the individual.
   Uuid
  ])
(record-pretty-printer/enable-record-pretty-printing PersonType)

;; This class contains attributes, which describe the temporal range of a specific collection. This
;; extent can be represented in a variety of ways: Range Date Time Single Date Time Periodic Date
;; Time
(defrecord TemporalExtentType
  [
   ;; This attribute tells the system and ultimately the end user how temporal coverage is specified
   ;; for the collection.
   TemporalRangeType

   ;; The precision (position in number of places to right of decimal point) of seconds used in
   ;; measurement.
   PrecisionOfSeconds

   ;; This attribute will denote that a data collection which covers, temporally, a discontinuous
   ;; range, currently ends at the present date. This way, the granules, which comprise the data
   ;; collection, that are continuously being added to inventory need not update the data collection
   ;; metadata for each one.
   EndsAtPresentFlag

   ;; Stores the start and end date/time of a collection.
   RangeDateTimes

   SingleDateTimes

   ;; This entity contains the name of the temporal period in addition to the date, time, duration
   ;; unit, and value, and cycle duration unit and value. Used at the collection level to describe a
   ;; collection having granules, which cover a regularly occurring period.
   PeriodicDateTimes
  ])
(record-pretty-printer/enable-record-pretty-printing TemporalExtentType)

;; Represents Internet sites that contain information related to the data, as well as related
;; Internet sites such as project home pages, related data archives/servers, metadata extensions,
;; online software packages, web mapping services, and calibration/validation data.
(defrecord RelatedUrlType
  [
   ;; The Title is a one-line description of the resource, could be used a caption when a browse
   ;; image is displayed. The title is especially useful for images such as graphs and photos.
   Title

   ;; Provides information about the resource defined by the URL
   Description

   ContentType

   ;; The URL to the resource associated with the data set.
   URLs

   ;; The mime type of the online resource.
   MimeType

   ;; The size of a download or site.
   FileSize
  ])
(record-pretty-printer/enable-record-pretty-printing RelatedUrlType)

;; Defines a responsibility by role which is either an organization such as a data center, or
;; institution responsible for distributing, archiving, or processing the data, etc., or a person
;; such as an Investigator, a Technical Contact, Metadata Author, etc.
(defrecord ResponsibilityType
  [
   ;; This is the responsibility role.
   Role

   ;; This is the responsibility party - either a person or an organization.
   Party
  ])
(record-pretty-printer/enable-record-pretty-printing ResponsibilityType)

;; Encapsulates the data that describes the change that was made to either the metadata or data.
(defrecord LineageDateType
  [
   ;; The date something changed with the metadata or data.
   Date

   ;; Documents the type of change that happened.
   Type

   ;; Describes the change.
   Description

   ;; Documents who made the change.
   Responsibilities
  ])
(record-pretty-printer/enable-record-pretty-printing LineageDateType)

;; Orbit parameters for the collection used by the Orbital Backtrack Algorithm.
(defrecord OrbitParametersType
  [
   ;; Width of the swath at the equator in Kilometers.
   SwathWidth

   ;; Orbital period in decimal minutes.
   Period

   ;; Inclination of the orbit. This is the same as (180-declination) and also the same as the
   ;; highest latitude achieved by the satellite. Data Unit: Degree.
   InclinationAngle

   ;; Indicates the number of orbits.
   NumberOfOrbits

   ;; The latitude start of the orbit relative to the equator. This is used by the backtrack search
   ;; algorithm to treat the orbit as if it starts from the specified latitude. This is optional and
   ;; will default to 0 if not specified.
   StartCircularLatitude
  ])
(record-pretty-printer/enable-record-pretty-printing OrbitParametersType)

;; This entity contains the name of the temporal period in addition to the date, time, duration
;; unit, and value, and cycle duration unit and value. Used at the collection level to describe a
;; collection having granules, which cover a regularly occurring period.
(defrecord PeriodicDateTimeType
  [
   ;; The name given to the recurring time period. e.g. 'spring - north hemi.'
   Name

   ;; This attribute provides the date (day and time) of the first occurrence of this regularly
   ;; occurring period which is relevant to the collection, granule, or event coverage.
   StartDate

   ;; This attribute provides the date (day and time) of the end occurrence of this regularly
   ;; occurring period which is relevant to the collection, granule, or event coverage.
   EndDate

   ;; The unit specification for the period duration.
   DurationUnit

   ;; The number of PeriodDurationUnits in the RegularPeriodic period. e.g. the RegularPeriodic
   ;; event 'Spring-North Hemi' might have a PeriodDurationUnit='MONTH' PeriodDurationValue='3'
   ;; PeriodCycleDurationUnit='YEAR' PeriodCycleDurationValue='1' indicating that Spring-North Hemi
   ;; lasts for 3 months and has a cycle duration of 1 year. The unit for the attribute is the value
   ;; of the attribute PeriodDurationValue.
   DurationValue

   ;; The unit specification of the period cycle duration.
   PeriodCycleDurationUnit

   PeriodCycleDurationValue
  ])
(record-pretty-printer/enable-record-pretty-printing PeriodicDateTimeType)

;; consists of the organization ShortName, LongName and Uuid, which is the name of the organization
;; that distributes, archives, or processes the data.
(defrecord OrganizationNameType
  [
   ;; Short name of the organization.
   ShortName

   ;; Long name of the organization.
   LongName

   ;; Uuid of the organization.
   Uuid
  ])
(record-pretty-printer/enable-record-pretty-printing OrganizationNameType)

;; This entity contains the address details for each contact.
(defrecord AddressType
  [
   ;; An address line for the address, used for mailing or physical addresses of organizations or
   ;; individuals who serve as points of contact.
   StreetAddresses

   ;; The city of the person or organization.
   City

   ;; The state or province of the address.
   StateProvince

   ;; The zip or other postal code of the address.
   PostalCode

   ;; The country of the address.
   Country
  ])
(record-pretty-printer/enable-record-pretty-printing AddressType)

;; Describes the type of the URL.
(defrecord ContentTypeType
  [
   ;; The type of URL. These are keywords that a user selects
   Type

   ;; Describes the sub type of the URL. These are keywords that a user selects
   Subtype
  ])
(record-pretty-printer/enable-record-pretty-printing ContentTypeType)