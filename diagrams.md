# INE Alumni Backend - Architecture Diagrams (Mermaid)

## 1. High-Level System Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        CLIENT[Frontend Applications<br/>React/Angular/Vue]
        MOBILE[Mobile Apps]
        THIRD[Third-party Integrations]
    end

    subgraph "Presentation Layer - REST Controllers"
        AUTH_CTRL[AuthController<br/>/api/v1/auth]
        ADMIN_CTRL[AdminController<br/>/api/v1/admins]
        LAUREAT_CTRL[LaureatController<br/>/api/v1/laureats]
        EVENT_CTRL[EventController<br/>/api/v1/events]
        OFFER_CTRL[OfferController<br/>/api/v1/offers]
        COMPANY_CTRL[CompanyController<br/>/api/v1/companies]
        RESOURCE_CTRL[ResourceController<br/>/api/v1/resources]
        PROFILE_CTRL[ProfileController<br/>/api/v1/profile]
        CONTACT_CTRL[ContactController<br/>/api/contact]
        PASSWORD_CTRL[PasswordResetController<br/>/api/v1/password]
        EMAIL_CTRL[EmailVerificationController]
        FILE_CTRL[FileUploadController<br/>/api/v1/files]
    end

    subgraph "Security Layer"
        CORS[CORS Config]
        JWT_FILTER[JWT Auth Filter]
        JWT_UTILS[JWT Utils]
        EMAIL_AUTH[Email Verification<br/>Authorization Manager]
        AUTH_ENTRY[Auth Entry Point]
        ROLES[Roles & Permissions]
    end

    subgraph "Business Logic Layer - Services"
        AUTH_SVC[AuthService]
        ADMIN_SVC[AdminsService]
        USER_SVC[UserService]
        LAUREAT_SVC[LaureatService]
        COMPANY_SVC[CompanyService]
        OFFER_SVC[OfferService]
        EVENT_SVC[EventService]
        RESOURCE_SVC[ResourceService]
        PROFILE_SVC[ProfileService]
        CONTACT_SVC[ContactMessageService]
        PASSWORD_SVC[PasswordResetService]
        EMAIL_SVC[EmailVerificationService]
        DATA_INIT[DataInitializerService]
    end

    subgraph "Data Access Layer - Repositories"
        USER_REPO[(UserRepository)]
        ADMIN_REPO[(AdminRepository)]
        LAUREAT_REPO[(LaureatRepository)]
        COMPANY_REPO[(CompanyRepository)]
        OFFER_REPO[(OfferRepository)]
        EVENT_REPO[(EventRepository)]
        RESOURCE_REPO[(ResourceRepository)]
        EDUCATION_REPO[(EducationRepository)]
        EXPERIENCE_REPO[(ExperienceRepository)]
        SKILL_REPO[(SkillRepository)]
        CONTACT_REPO[(ContactMessageRepository)]
        OFFER_APP_REPO[(OfferApplicationRepository)]
        REVIEW_REPO[(CompanyReviewRepository)]
        LINK_REPO[(ExternalLinkRepository)]
    end

    subgraph "Persistence Layer"
        POSTGRES[(PostgreSQL<br/>Database)]
        REDIS[(Redis<br/>Cache)]
    end

    subgraph "External Services"
        SMTP[Email Service<br/>SMTP/Gmail]
        FILE_STORAGE[File Storage<br/>/uploads]
    end

    CLIENT --> AUTH_CTRL
    CLIENT --> LAUREAT_CTRL
    MOBILE --> OFFER_CTRL
    THIRD --> COMPANY_CTRL

    AUTH_CTRL --> CORS
    ADMIN_CTRL --> JWT_FILTER
    LAUREAT_CTRL --> JWT_FILTER
    EVENT_CTRL --> JWT_FILTER
    OFFER_CTRL --> JWT_FILTER

    JWT_FILTER --> JWT_UTILS
    JWT_FILTER --> EMAIL_AUTH
    JWT_UTILS --> ROLES

    AUTH_CTRL --> AUTH_SVC
    ADMIN_CTRL --> ADMIN_SVC
    LAUREAT_CTRL --> LAUREAT_SVC
    COMPANY_CTRL --> COMPANY_SVC
    OFFER_CTRL --> OFFER_SVC
    EVENT_CTRL --> EVENT_SVC
    RESOURCE_CTRL --> RESOURCE_SVC
    PROFILE_CTRL --> PROFILE_SVC
    CONTACT_CTRL --> CONTACT_SVC
    PASSWORD_CTRL --> PASSWORD_SVC
    EMAIL_CTRL --> EMAIL_SVC

    AUTH_SVC --> USER_REPO
    ADMIN_SVC --> ADMIN_REPO
    LAUREAT_SVC --> LAUREAT_REPO
    COMPANY_SVC --> COMPANY_REPO
    OFFER_SVC --> OFFER_REPO
    EVENT_SVC --> EVENT_REPO
    RESOURCE_SVC --> RESOURCE_REPO
    PROFILE_SVC --> EDUCATION_REPO
    PROFILE_SVC --> EXPERIENCE_REPO
    PROFILE_SVC --> SKILL_REPO
    CONTACT_SVC --> CONTACT_REPO
    OFFER_SVC --> OFFER_APP_REPO
    COMPANY_SVC --> REVIEW_REPO

    USER_REPO --> POSTGRES
    ADMIN_REPO --> POSTGRES
    LAUREAT_REPO --> POSTGRES
    COMPANY_REPO --> POSTGRES
    OFFER_REPO --> POSTGRES
    EVENT_REPO --> POSTGRES
    RESOURCE_REPO --> POSTGRES
    CONTACT_REPO --> POSTGRES

    AUTH_SVC --> REDIS
    PASSWORD_SVC --> REDIS

    CONTACT_SVC --> SMTP
    EMAIL_SVC --> SMTP
    PASSWORD_SVC --> SMTP

    FILE_CTRL --> FILE_STORAGE

    style CLIENT fill:#e1f5ff
    style POSTGRES fill:#336791,color:#fff
    style REDIS fill:#DC382D,color:#fff
    style SMTP fill:#EA4335,color:#fff
```

## 2. Security Flow

```mermaid
sequenceDiagram
    participant Client
    participant CORSFilter
    participant JWTFilter
    participant JWTUtils
    participant EmailAuthManager
    participant Controller
    participant Service

    Client->>CORSFilter: HTTP Request
    CORSFilter->>CORSFilter: Validate CORS
    CORSFilter->>JWTFilter: Forward Request
    
    alt Has JWT Token
        JWTFilter->>JWTUtils: Validate Token
        JWTUtils->>JWTFilter: Token Valid
        JWTFilter->>JWTFilter: Load UserDetails
        JWTFilter->>EmailAuthManager: Check Email Verified
        
        alt Email Verified
            EmailAuthManager->>Controller: Allow Access
            Controller->>Controller: Check @PreAuthorize
            
            alt Has Authority
                Controller->>Service: Process Request
                Service-->>Client: Success Response
            else No Authority
                Controller-->>Client: 403 Forbidden
            end
        else Email Not Verified
            EmailAuthManager-->>Client: 403 Email Not Verified
        end
    else No Token (Public Endpoint)
        JWTFilter->>Controller: Allow Public Access
        Controller->>Service: Process Request
        Service-->>Client: Success Response
    end
```

## 3. Authentication Flow

```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant AuthService
    participant UserService
    participant PasswordEncoder
    participant JWTUtils
    participant UserRepository
    participant EmailService
    participant Database

    Note over Client,Database: Sign Up Flow
    Client->>AuthController: POST /api/v1/auth/signup
    AuthController->>AuthService: signUpUser(SignUpRequestDto)
    AuthService->>UserService: existsByEmail(email)
    UserService->>UserRepository: existsByEmail(email)
    UserRepository->>Database: SELECT email
    Database-->>UserRepository: false
    UserRepository-->>UserService: false
    UserService-->>AuthService: false
    
    AuthService->>PasswordEncoder: encode(password)
    PasswordEncoder-->>AuthService: hashedPassword
    AuthService->>UserRepository: save(user)
    UserRepository->>Database: INSERT user
    Database-->>UserRepository: saved user
    UserRepository-->>AuthService: saved user
    
    AuthService->>EmailService: sendVerificationEmail(user)
    EmailService-->>AuthService: email sent
    AuthService-->>AuthController: success
    AuthController-->>Client: 201 Created

    Note over Client,Database: Sign In Flow
    Client->>AuthController: POST /api/v1/auth/signin
    AuthController->>AuthService: signInUser(SignInRequestDto)
    AuthService->>AuthService: authenticate(email, password)
    AuthService->>JWTUtils: generateJwtToken(email)
    JWTUtils-->>AuthService: JWT token
    AuthService->>UserService: getUserByEmail(email)
    UserService->>UserRepository: findByEmail(email)
    UserRepository->>Database: SELECT user
    Database-->>UserRepository: user
    UserRepository-->>UserService: user
    UserService-->>AuthService: user
    AuthService-->>AuthController: SignInResponseDto
    AuthController-->>Client: 200 OK + JWT
```

## 4. Alumni Search & Filter Flow

```mermaid
sequenceDiagram
    participant Client
    participant LaureatController
    participant LaureatService
    participant LaureatRepository
    participant Database

    Client->>LaureatController: GET /api/v1/laureats?major=DATA&page=0&size=12
    LaureatController->>LaureatService: getAllLaureats(filters, pagination)
    
    LaureatService->>LaureatService: Build JPA Specification
    LaureatService->>LaureatService: Create PageRequest
    
    LaureatService->>LaureatRepository: findAll(specification, pageable)
    LaureatRepository->>Database: SELECT with WHERE and LIMIT
    Database-->>LaureatRepository: List<Laureat>
    LaureatRepository-->>LaureatService: Page<Laureat>
    
    LaureatService->>LaureatService: Map to LaureatDTO
    LaureatService-->>LaureatController: PageResponseDTO<LaureatDTO>
    LaureatController-->>Client: 200 OK + ApiResponseDto
```

## 5. Entity Relationship Diagram (Corrected)

> **Note:** This diagram represents the actual database tables and their relationships.
> - `inpt_users` uses Single Table Inheritance: it stores `Laureat` and `Ine` users, differentiated by a `user_type` column.
> - `admins` is a separate table for `Admin` users. Both inherit fields from the `User` MappedSuperclass.
> - `Offer` is decoupled from `Company`; it stores the company name as a text field, not a foreign key.
> - Enums (like Role, Major, etc.) are attributes on the tables, not separate tables.

```mermaid
erDiagram
    inpt_users {
        Long id PK
        String user_type "Discriminator"
        String email UK
        String password
        String major
        Integer graduationYear
        String currentPosition "For Laureat"
        Long current_company_id FK "For Laureat"
    }

    admins {
        Long id PK
        String email UK
        String password
        String fullName
    }

    companies {
        Long id PK
        String name
        String website
        String location
    }

    offers {
        Long id PK
        String title
        String company "Stores company name as text"
        String location
        String type "Enum: OfferType"
    }

    offer_applications {
        Long id PK
        Long offer_id FK
        Long applicant_id FK
        LocalDateTime appliedAt
    }

    educations {
        Long id PK
        Long laureat_id FK
        String institutionName
        String degree
    }

    experiences {
        Long id PK
        Long laureat_id FK
        Long company_id FK
        String jobTitle
    }

    skills {
        Long id PK
        Long laureat_id FK
        String name
        String category "Enum: SkillCategory"
    }

    company_reviews {
        Long id PK
        Long company_id FK
        Long laureat_id FK
        Integer rating
    }

    external_links {
        Long id PK
        Long laureat_id FK "Nullable"
        Long company_id FK "Nullable"
        String url
    }

    events {
        Long id PK
        String title
        String location
        LocalDateTime date
    }
    
    resources {
        Long id PK
        String title
        String link
        String category "Enum: Category"
        String domain "Enum: Domain"
    }

    contact_messages {
        Long id PK
        String email
        String message
    }

    inpt_users }o--|| companies : "has current"
    inpt_users ||--|{ educations : "has"
    inpt_users ||--|{ experiences : "has"
    inpt_users ||--|{ skills : "has"
    inpt_users ||--|{ company_reviews : "writes"
    inpt_users ||--|{ offer_applications : "applies to"
    inpt_users ||--|{ external_links : "has"
    
    companies ||--|{ experiences : "provides"
    companies ||--|{ company_reviews : "receives"
    companies ||--|{ external_links : "has"

    offers ||--|{ offer_applications : "receives"
```

## 6. Component Diagram

```mermaid
graph LR
    subgraph "Frontend"
        WEB[Web App]
        MOBILE[Mobile App]
    end

    subgraph "Spring Boot Application"
        subgraph "Controllers"
            C1[Auth]
            C2[Alumni]
            C3[Companies]
            C4[Offers]
            C5[Events]
            C6[Resources]
        end

        subgraph "Security"
            SEC1[JWT Filter]
            SEC2[CORS Config]
            SEC3[Auth Manager]
        end

        subgraph "Services"
            S1[Auth Service]
            S2[Alumni Service]
            S3[Company Service]
            S4[Offer Service]
            S5[Event Service]
            S6[Email Service]
        end

        subgraph "Repositories"
            R1[User Repo]
            R2[Alumni Repo]
            R3[Company Repo]
            R4[Offer Repo]
            R5[Event Repo]
        end
    end

    subgraph "Data Layer"
        DB[(PostgreSQL)]
        CACHE[(Redis)]
    end

    subgraph "External"
        MAIL[SMTP Server]
        FILES[File System]
    end

    WEB --> SEC2
    MOBILE --> SEC2
    SEC2 --> SEC1
    SEC1 --> C1
    SEC1 --> C2
    SEC1 --> C3
    
    C1 --> S1
    C2 --> S2
    C3 --> S3
    C4 --> S4
    C5 --> S5
    
    S1 --> R1
    S2 --> R2
    S3 --> R3
    S4 --> R4
    S5 --> R5
    
    R1 --> DB
    R2 --> DB
    R3 --> DB
    
    S1 --> CACHE
    S6 --> MAIL
    C6 --> FILES

    style DB fill:#336791,color:#fff
    style CACHE fill:#DC382D,color:#fff
    style MAIL fill:#EA4335,color:#fff
```

## 7. Deployment Diagram

```mermaid
graph TB
    subgraph "Client Tier"
        BROWSER[Web Browser]
        APP[Mobile App]
    end

    subgraph "Application Server"
        SPRINGBOOT[Spring Boot Application<br/>Port: 8080]
        UPLOADS[File Storage<br/>/uploads]
    end

    subgraph "Database Tier"
        POSTGRES[(PostgreSQL<br/>Port: 5432)]
        REDIS[(Redis<br/>Port: 6379)]
    end

    subgraph "External Services"
        GMAIL[Gmail SMTP<br/>Port: 587]
    end

    BROWSER -->|HTTPS| SPRINGBOOT
    APP -->|HTTPS| SPRINGBOOT
    
    SPRINGBOOT -->|JDBC| POSTGRES
    SPRINGBOOT -->|Redis Protocol| REDIS
    SPRINGBOOT -->|SMTP| GMAIL
    SPRINGBOOT -->|File I/O| UPLOADS

    style SPRINGBOOT fill:#6DB33F,color:#fff
    style POSTGRES fill:#336791,color:#fff
    style REDIS fill:#DC382D,color:#fff
    style GMAIL fill:#EA4335,color:#fff
```

## 8. Data Model Class Diagram (Corrected)

> **Note:** This diagram shows the actual class inheritance and composition structure as defined in the Java code.
> - `User` is an abstract `@MappedSuperclass`.
> - `InptUser` and `Admin` are concrete `@Entity` classes that inherit from `User`.
> - `Laureat` and `Ine` inherit from `InptUser` and are part of a `@SingleTable` inheritance strategy.
> - Properties like `Role`, `Major`, `Permission` are `enum` types, not distinct classes.

```mermaid
classDiagram
    direction LR

    class User {
        <<MappedSuperclass>>
        # Long id
        # String fullName
        # String email
        # String password
        # Role role
        # Boolean isAccountVerified
        # Boolean isEmailVerified
    }

    class Admin {
        <<Entity>>
    }

    class InptUser {
        <<Entity>>
        # Major major
        # Integer graduationYear
        # Gender gender
        # String userType
    }

    class InptUser {
        <<Entity>>
        # Major major
        # Integer graduationYear
        # Gender gender
        # String userType
    }

    class Laureat {
        <<Entity>>
        # Company currentCompany
        # String bio
        # String currentPosition
        # List~Education~ educations
        # List~Experience~ experiences
        # List~Skill~ skills
        # List~ExternalLink~ externalLinks
        # List~CompanyReview~ companyReviews
    }

    class Company {
        <<Entity>>
        + String name
        + String location
        + List~Laureat~ currentEmployees
        + List~Experience~ experiences
        + List~CompanyReview~ reviews
    }
    
    class Experience {
        <<Entity>>
        + String jobTitle
        + Company company
        + Laureat laureat
    }

    class Education {
        <<Entity>>
        + String institutionName
        + String degree
        + Laureat laureat
    }
    
    class Skill {
        <<Entity>>
        + String name
        + Laureat laureat
    }

    class CompanyReview {
        <<Entity>>
        + Integer rating
        + Company company
        + Laureat laureat
    }

    class Offer {
        <<Entity>>
        + String title
        + String company "Note: String, not FK"
        + OfferType type
        + List~OfferApplication~ applications
    }

    class OfferApplication {
        <<Entity>>
        + Offer offer
        + InptUser applicant
    }

    User <|-- Admin
    User <|-- InptUser
    InptUser <|-- Laureat
    InptUser <|-- Ine

    Laureat "1" -- "0..1" Company : "works at"
    Laureat "1" -- "0..*" Education : "has"
    Laureat "1" -- "0..*" Experience : "has"
    Laureat "1" -- "0..*" Skill : "has"
    Laureat "1" -- "0..*" CompanyReview : "writes"
    
    Company "1" -- "0..*" Experience : "provides"
    Company "1" -- "0..*" CompanyReview : "receives review for"
    
    Experience "0..*" -- "1" Company
    Experience "0..*" -- "1" Laureat

    Offer "1" -- "0..*" OfferApplication : "has"
    OfferApplication "0..*" -- "1" InptUser : "is submitted by"

```

## 9. State Diagram - Offer Application

```mermaid
stateDiagram-v2
    [*] --> Draft: Create Application
    Draft --> Submitted: Submit Application
    Submitted --> UnderReview: Company Reviews
    UnderReview --> Shortlisted: Candidate Selected
    UnderReview --> Rejected: Candidate Not Selected
    Shortlisted --> Interviewed: Schedule Interview
    Interviewed --> Offered: Extend Offer
    Interviewed --> Rejected: Not Selected
    Offered --> Accepted: Candidate Accepts
    Offered --> Declined: Candidate Declines
    Accepted --> [*]
    Rejected --> [*]
    Declined --> [*]
```

## 10. Activity Diagram - User Registration

```mermaid
flowchart TD
    Start([User Starts Registration]) --> InputData[Enter Name, Email, Password]
    InputData --> ValidateInput{Validate Input}
    ValidateInput -->|Invalid| ShowError[Show Validation Error]
    ShowError --> InputData
    ValidateInput -->|Valid| CheckEmail{Email Exists?}
    CheckEmail -->|Yes| EmailError[Show Email Already Exists]
    EmailError --> InputData
    CheckEmail -->|No| HashPassword[Hash Password with BCrypt]
    HashPassword --> SaveUser[Save User to Database]
    SaveUser --> GenerateToken[Generate Verification Token]
    GenerateToken --> SendEmail[Send Verification Email]
    SendEmail --> Success[Return Success Response]
    Success --> End([Registration Complete])
```
