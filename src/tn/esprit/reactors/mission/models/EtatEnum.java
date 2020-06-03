package tn.esprit.reactors.mission.models;

public enum EtatEnum{
    inviter,
    accepter,
    réfuser;

    public static String getEtat(String etat) {
        switch (etat) {
            case "accepter": return "accepter";
            case "réfuser":return "réfuser";
            case "inviter":return "inviter";
            default:return "inviter";
        }
    }
}
