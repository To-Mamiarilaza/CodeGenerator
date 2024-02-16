
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;

[ApiController]
[Route("/pays")]
public class PaysController : ControllerBase {
    
    private readonly DatabaseContext _context;
    
    public PaysController(DatabaseContext context) {
        _context = context;
    }
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Pays>>> GetAllPayss() {
        return await _context.Payss.ToListAsync();
    }
    
    [HttpGet("{idPays}")]
    public async Task<ActionResult<Pays>> GetPaysById(int idPays) {
        var pays = await _context.Payss.FirstOrDefaultAsync(p => p.IdPays == idPays);
        
        if(pays == null) {
            return NotFound();
        }
        
        return pays;
    }
    
    [HttpPost]
    public async Task<ActionResult<Pays>> CreatePays(Pays pays) {
        _context.Payss.Add(pays);
        await _context.SaveChangesAsync();
        
        return CreatedAtAction(nameof(GetPaysById), new { idPays = pays.IdPays }, pays);
    }
    
    [HttpPut("{idPays}")]
    public async Task<IActionResult> UpdatePays(int idPays, Pays pays) {
        if (idPays != pays.IdPays)
        {
            return BadRequest();
        }
        
        _context.Entry(pays).State = EntityState.Modified;
        
        try
        {
            await _context.SaveChangesAsync();
        }
        catch(DbUpdateConcurrencyException)
        {
            throw;
        }
        
        return NoContent();
    }
    
    [HttpDelete("{idPays}")]
    public async Task<IActionResult> DeletePays(int idPays) {
        var pays = await _context.Payss.FindAsync(idPays);
        if (pays == null)
        {
            return NotFound();
        }
        
        _context.Payss.Remove(pays);
        await _context.SaveChangesAsync();
        
        return NoContent();
    }
    
}
