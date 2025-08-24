# National Museum Android App
Using Java, XML & Android Studio

# Flowchart

mermaid
graph TD
    subgraph "Authentication"
        A[App Start] --> B{User Logged In?};
        B -- No --> C[Login Screen (LoginActivity)];
        C -- "Login Success" --> E[Main App Screen (MainActivity)];
        C -- "Needs to Sign Up" --> D[Sign Up Screen (SignUpActivity)];
        D -- "Sign Up Success" --> E;
        B -- Yes --> E;
    end

    subgraph "Main Application"
        E --> E_NavDrawer["Navigation Drawer"];
        E_NavDrawer -- "Select Home" --> E_Home[Home Content];
        E_NavDrawer -- "Select Gallery" --> E_Gallery[Gallery Content];
        E_NavDrawer -- "Select Museums" --> E_Museums[Museums Content];
        E_NavDrawer -- "Select Collections" --> E_Collections[Collections Content];

        E -- "Options Menu" --> E_Options;
        E_Options -- "Settings" --> F[Settings Screen (SettingsActivity)];
        E_Options -- "About Us" --> G[About Us Screen (AboutUsActivity)];
        E_Options -- "Blogs" --> H[Blogs Screen (BlogsActivity)];
        E_Options -- "Profile" --> I[Profile Screen (ProfileActivity)];
    end

    subgraph "User Profile Management"
        I -- "View Profile Details" --> I_Details["Display User Info (from Firebase)"];
        I -- "Edit Profile" --> J[Edit Profile Screen (EditProfileActivity)];
        J -- "Save Changes" --> I;
        I -- "Change Password" --> K[Change Password Screen (ChangePasswordActivity)];
        K -- "Update Password" --> I;
        I -- "Logout" --> C;
    end
