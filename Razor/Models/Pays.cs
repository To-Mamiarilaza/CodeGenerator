
namespace Models;

using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


[Table("pays")]
public class Pays {
    /// Field
    [Key]
    [Column("id_pays")]
    public int IdPays { get; set; }
    
    [Column("pays_name")]
    public string PaysName { get; set; }
    
    
    
    /// Constructor
    public Pays(){}
    
    
    /// Getter and setter
    
}
