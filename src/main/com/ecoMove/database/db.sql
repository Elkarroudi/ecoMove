DROP TABLE IF EXISTS partners, contracts, offers, tickets;

CREATE TYPE TransportType as ENUM  ('PLAN' , 'BUS' , 'TRAIN', 'TAXI');
CREATE TYPE TicketStatus as ENUM ('AVAILABLE' , 'SOLD' , 'CANCELLED');
CREATE TYPE ContractStatus as ENUM ('ONGOING' , 'TERMINATED' , 'SUSPENDED');
CREATE TYPE PartnerStatus as ENUM ('ACTIVE' , 'EXPIRED' , 'SUSPENDED');
CREATE TYPE DiscountType as ENUM ('PERCENTAGE' , 'FLAT');
CREATE TYPE OfferStatus as ENUM ('ACTIVE' , 'EXPIRED' , 'SUSPENDED');

CREATE TABLE IF NOT EXISTS partners (
    id VARCHAR(255) PRIMARY KEY,
    companyName VARCHAR(255),
    commercialContact VARCHAR(255),
    transportationType TransportType,
    geographicalArea VARCHAR(255),
    specialConditions TEXT,
    partnerStatus PartnerStatus,
    creationDate TIMESTAMP,
    createdAt TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE IF NOT EXISTS contracts (
    id VARCHAR(255) PRIMARY KEY,
    partnerId VARCHAR(255),
    startDate TIMESTAMP,
    endDate  TIMESTAMP,
    specialRate DOUBLE PRECISION,
    agreementConditions VARCHAR(255),
    renewable BOOLEAN,
    contractStatus ContractStatus,
    createdAt TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (partnerId) REFERENCES partners(id)
);

CREATE TABLE IF NOT EXISTS offers (
    id VARCHAR(255) PRIMARY KEY,
    contractId VARCHAR(255),
    offerName VARCHAR(255),
    offerDescription TEXT,
    startDate TIMESTAMP,
    endDate TIMESTAMP,
    discountType DiscountType,
    discountValue DOUBLE PRECISION,
    conditions TEXT,
    offerStatus OfferStatus,
    createdAt TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (contractId) REFERENCES contracts(id)

);

CREATE TABLE IF NOT EXISTS tickets (
    id VARCHAR(255) PRIMARY KEY,
    contractId VARCHAR(255),
    transportType TransportType,
    purchasePrice DOUBLE PRECISION,
    salePrice DOUBLE PRECISION,
    soldDate TIMESTAMP,
    ticketStatus TicketStatus,
    createdAt TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (contractId) REFERENCES contracts(id)
);