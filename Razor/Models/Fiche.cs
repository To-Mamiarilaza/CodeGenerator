
namespace Models;

using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


[Table("fiche")]
public class Fiche {
    /// Field
    [Key]
    [Column("id_fiche")]
    public int IdFiche { get; set; }
    
    [Column("name")]
    public string Name { get; set; }
    
    [Column("firstname")]
    public string Firstname { get; set; }
    
    [Column("address")]
    public string Address { get; set; }
    
    [Column("mail")]
    public string Mail { get; set; }
    
    [Column("etat")]
    public int Etat { get; set; }
    
    [Column("salaire")]
    public double Salaire { get; set; }
    
    [Column("admin")]
    public bool Admin { get; set; }
    
    
    
    /// Constructor
    public Fiche(){}
    
    
    /// Getter and setter
    
}
